package com.anthem53LMS.service.file;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.file.FileEntity;
import com.anthem53LMS.domain.file.FileEntityRepository;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.lecture_assignment.LectureAsssignmentRepository;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.lectureDto.SubmittedFileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

        FileEntity fileEntity = FileEntity.builder().rawName(rawName).uuid(uuid).savedPath(savedPath).build();

        file.transferTo(new File(savedPath));

        String is_check_debug = "Not checked";
        for  (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            if (temp.getUser() == user){
                temp.getFileList().add(fileEntity);
                fileEntity.setSubmittedFile(temp);
                is_check_debug = "Checked";
                break;
            }
        }
        System.out.println("*********"+is_check_debug+"************");
        for  (SubmittedFile temp : lectureAssignment.getSubmittedFileSet()){
            if (temp.getUser() == user){
                for (FileEntity tempFile : temp.getFileList()){
                    System.out.println("file name : " + tempFile.getOriginalName());
                }
            }
        }

        Long id = fileEntityRepository.save(fileEntity).getId();

        return id;
    }


    @Transactional
    public List<SubmittedFileResponseDto> findSubmittedFileList(Long assignment_id, SessionUser sessionUser){
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
            return targetList.stream().map(SubmittedFileResponseDto::new).collect(Collectors.toList());
        }


        /*    Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

            return lecture.getLectureLessons().stream().map(LectureLessonListDto::new).collect(Collectors.toList());*/

    }

    public void fileDelete(Long file_id){
        FileEntity fileEntity = fileEntityRepository.findById(file_id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
        SubmittedFile submittedFile = fileEntity.getSubmittedFile();



        System.out.println(submittedFile.getFileList());
        submittedFile.getFileList().remove(fileEntity);
        System.out.println(submittedFile.getFileList());

        File file = new File(fileEntity.getSavedPath());
        file.delete();
        fileEntity.setSubmittedFile(null);

        fileEntityRepository.delete(fileEntity);

    }
}
