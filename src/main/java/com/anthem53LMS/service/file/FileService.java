package com.anthem53LMS.service.file;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.file.FileEntity;
import com.anthem53LMS.domain.file.FileEntityRepository;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.lecture_assignment.LectureAsssignmentRepository;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.lectureDto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;
    private final FileEntityRepository fileEntityRepository;
    private final UserRepository userRepository;
    private final LectureAsssignmentRepository lectureAsssignmentRepository;
    private final LectureRepository lectureRepository;

    private Long maxSum = 20000000l;

    @Transactional
    public Long fileSave (MultipartFile file , Long assignment_id, SessionUser sessionUser) throws IOException {
        if (file.isEmpty() == true){
            return null;
        }

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));


        String rawName =file.getOriginalFilename();
        String uuid  = UUID.randomUUID().toString();
        String extension = rawName.substring(rawName.lastIndexOf("."));

        String savedName = uuid + extension;
        String savedPath = fileDir + savedName;
        Long filesize = file.getSize();

        System.out.println("[Filesize]");
        System.out.println(filesize);
        System.out.println("[**********]");

        FileEntity fileEntity = FileEntity.builder().rawName(rawName).uuid(uuid).savedPath(savedPath).fileSize(filesize).build();

        file.transferTo(new File(savedPath));

        String is_check_debug = "Not checked";
        for  (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            if (temp.getUser() == user){
                temp.getFileList().add(fileEntity);
                temp.addFileSize(filesize);
                fileEntity.setSubmittedFile(temp);

                is_check_debug = "Checked";
                break;
            }
        }

        Long id = fileEntityRepository.save(fileEntity).getId();

        return id;
    }

    @Transactional
    public boolean checkFilesSize(MultipartFile[] fileList , Long assignment_id , SessionUser sessionUser){
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        SubmittedFile submittedFile = null;
        for (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            if (temp.getUser() == user){
                submittedFile = temp;
                break;
            }
        }

        Long current_size = submittedFile.getTotolFileSize();
        Long new_file_size = 0l;
        for (MultipartFile file : fileList){
            new_file_size += file.getSize();

        }

        if (current_size + new_file_size <= 20000000){
            return true;
        }
        else{
            return false;
        }

    }

    @Transactional
    public List<FileResponseDto> findSubmittedFileList(Long assignment_id, SessionUser sessionUser){
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        List<FileEntity> targetList = null;
        for  (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            System.out.println(temp.getUser().getName());

            if (temp.getUser() == user){
                targetList = temp.getFileList();
                break;
            }
            else{;}
        }

        if (targetList == null){
            System.out.println("Fileservice findSubmittedFileList targetList is Null");
            return null;
        }
        else{
            if (targetList.isEmpty() != true){
                System.out.println(targetList.get(0).getOriginalName());
            }
            return targetList.stream().map(FileResponseDto::new).collect(Collectors.toList());
        }


    }


    @Transactional
    public void fileDelete(Long file_id){
        FileEntity fileEntity = fileEntityRepository.findById(file_id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
        SubmittedFile submittedFile = fileEntity.getSubmittedFile();

        submittedFile.subFileSize(fileEntity.getFileSize());
        submittedFile.getFileList().remove(fileEntity);


        File file = new File(fileEntity.getSavedPath());
        file.delete();
        fileEntity.setSubmittedFile(null);

        fileEntityRepository.delete(fileEntity);
    }

    @Transactional

    public void fileAllDelete(SessionUser sessionUser, Long assignment_id){

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        List<FileEntity> targetList = null;
        SubmittedFile submittedFile = null;
        for  (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            System.out.println(temp.getUser().getName());

            if (temp.getUser() == user){
                targetList = temp.getFileList();
                submittedFile = temp;
                break;
            }
            else{;}
        }

        if (targetList == null){
            System.out.println("He/She is Not attendee!");
        }
        else{
            for (FileEntity temp : targetList){
                File file = new File(temp.getSavedPath());
                file.delete();
                fileEntityRepository.delete(temp);
            }
            submittedFile.setFileSize(0l);
            targetList.clear();

        }



    }


    @Transactional
    public void fileDownload(Long file_id, HttpServletResponse response) throws IOException{
        FileEntity fileEntity = fileEntityRepository.findById(file_id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));

        File file = new File(fileEntity.getSavedPath());

        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int)file.length());
        //response.setHeader("Content-disposition", "attachment;filename=\"" + file + "\"");
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileEntity.getOriginalName() + "\"");

        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(file);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();

    }


}
