package com.anthem53LMS.service.lectures;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.courseRegistration.CourseRegistration;
import com.anthem53LMS.domain.courseRegistration.CourseRegistrationRepository;
import com.anthem53LMS.domain.file.FileEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.lecture_assignment.LectureAsssignmentRepository;
import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import com.anthem53LMS.domain.lecture_notice.LectureNoticeRepository;
import com.anthem53LMS.domain.lesson.LectureLesson;
import com.anthem53LMS.domain.lesson.LectureLessonRepository;
import com.anthem53LMS.domain.message.Message;
import com.anthem53LMS.domain.message.MessageRepository;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFileRepository;
import com.anthem53LMS.domain.user.Role;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.service.file.FileService;
import com.anthem53LMS.web.Dto.*;
import com.anthem53LMS.web.lectureDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class LecturesService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureNoticeRepository lectureNoticeRepository;
    private final LectureLessonRepository lectureLessonRepository;
    private final LectureAsssignmentRepository lectureAsssignmentRepository;
    private final MessageRepository messageRepository;
    private final FileService fileService;

    private final SubmittedFileRepository submittedFileRepository;




    private final CourseRegistrationRepository courseRegistrationRepository;
    @Transactional
    public Long lectureSave(lecturesSaveRequestDto requestDto, SessionUser sUser){

        String userEmail = sUser.getEmail();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. Email ="+userEmail));

        requestDto.setSubLecturer(user.getSubLecturer());
        if (requestDto.getTitle().equals("")){
            requestDto.setTitle("강의제목 - 무제");
            
        }

        Lecture lecture = requestDto.toEntity();

        Long id = lectureRepository.save(lecture).getId();

        return id;
    }
    @Transactional(readOnly = true)
    public List<lecturesListResponseDto> findAllDesc(){

        return lectureRepository.findAllDesc().stream().map(lecturesListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public LecturesResponseDto findById( Long id) {
        Lecture entity = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        return new LecturesResponseDto(entity);
    }

    @Transactional
    public Long LectureRegister(LectureRegisterRequestDto requestDto, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        Lecture lecture = lectureRepository.findById(requestDto.getLecture_id()).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));;

        CourseRegistration courseRegistration = new CourseRegistration(lecture,user);

        user.getCurrent_Lectures().add(courseRegistration);
        lecture.getCurrent_Attendees().add(courseRegistration);

        for (LectureAssignment lectureAssignment : lecture.getLectureAssignment()){

            SubmittedFile submittedFile = new SubmittedFile(lectureAssignment, user);

            user.getSubmittedFile().add(submittedFile);
            lectureAssignment.getSubmittedFileSet().add(submittedFile);

        }



        Long id = courseRegistrationRepository.save(courseRegistration).getId();
        return id;
    }

    @Transactional
    public Set<LectureRegisterListResponseDto> findAttendedLecture(SessionUser sessionUser){
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));


        return user.getCurrent_Lectures().stream().map(LectureRegisterListResponseDto::new).collect(Collectors.toSet());
    }


    @Transactional
    public LectureTakeViewRespondDto findLectureTitleAndContent(Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));
        LectureTakeViewRespondDto respondDto = new LectureTakeViewRespondDto(lecture);

        return respondDto;
    }

    @Transactional
    public Long LectureNoticeSave(LectureNoticeSaveRequestDto respondDto, Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        LectureNotice lectureNotice = new LectureNotice(respondDto);
        lecture.getLectureNotices().add(lectureNotice);
        lectureNotice.setLecture(lecture);

        Long notice_id = lectureNoticeRepository.save(lectureNotice).getId();
        String tempLink = "/showLecture/register/take_course/"+String.valueOf(lecture_id)+"/notice/"+String.valueOf(notice_id);
        Long message_id =saveMessage(lecture_id,"공지사항이 등록 되었습니다.",tempLink);


        return notice_id;

    }

    @Transactional
    public List<LectureRegisterListResponseDto> findAllOwnLecture(SessionUser sessionUser){
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다."));

        return user.getSubLecturer().getLectures().stream().map(LectureRegisterListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<LectureNoticeListResponseDto> findLectureNotice(Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        return lecture.getLectureNotices().stream().map(LectureNoticeListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional
    public LectureNoticeResponseDto findLectureNoticeInfo(Long lecture_id, Long notice_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));
        LectureNotice lectureNotice = lectureNoticeRepository.findById(notice_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        LectureNoticeResponseDto responseDto = new LectureNoticeResponseDto(lectureNotice);

        return responseDto;

    }

    @Transactional
    public List<LectureLessonListDto> findLectureLesson (Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        return lecture.getLectureLessons().stream().map(LectureLessonListDto::new).collect(Collectors.toList());

    }


    @Transactional
    public Long LectureLessonSave(LectureLessonSaveRequestDto requestDto, Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));


        requestDto.setLink(processYoutubeLink(requestDto.getLink()));

        LectureLesson lectureLesson = requestDto.toEntity();
        lecture.getLectureLessons().add(lectureLesson);
        lectureLesson.setLecture(lecture);
        lectureLesson.setSequence(lecture.getLectureLessons().size());

        Long lesson_id =lectureLessonRepository.save(lectureLesson).getId();

        String tmepLink = "http://localhost:8080/showLecture/register/take_course/"+String.valueOf(lecture_id) +"/lesson/"+String.valueOf(lesson_id);
        saveMessage(lecture_id,"새로운 수업이 등록 되었습니다.", tmepLink);

        return lesson_id;
    }

    @Transactional
    public LectureLessonResponseDto findLessonInfo(Long lecture_id, Long lesson_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        LectureLesson lectureLesson = lectureLessonRepository.findById(lesson_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        LectureLessonResponseDto lectureLessonResponseDto = new LectureLessonResponseDto(lectureLesson);

        return lectureLessonResponseDto;


    }

    @Transactional
    public List<LectureAssignmentListResponseDto> findLectureAssignment(Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        return lecture.getLectureAssignment().stream().map(LectureAssignmentListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional
    public Long LectureAssignmentSave(LectureAssignmentSaveRequestDto requestDto, Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        requestDto.setLecture(lecture);

        if (requestDto.getTitle().equals("")){
            requestDto.setTitle("과제 제목 없음.");
        }

        LectureAssignment lectureAssignment = requestDto.toEntity();

        for (CourseRegistration attendeesInfoItem : lecture.getCurrent_Attendees()){

            User attendee = attendeesInfoItem.getUser();
            SubmittedFile submittedFile = new SubmittedFile(lectureAssignment, attendee);

            attendee.getSubmittedFile().add(submittedFile);
            lectureAssignment.getSubmittedFileSet().add(submittedFile);
            System.out.println("Attendee : " + attendee.getName());
        }

        System.out.println("attendee num ");
        System.out.println(lecture.getCurrent_Attendees().size());

        Long assign_id = lectureAsssignmentRepository.save(lectureAssignment).getId();
        String tempLink = "http://localhost:8080/showLecture/register/take_course/"+String.valueOf(lecture_id)+"/assignment/"+String.valueOf(assign_id);
        saveMessage(lecture_id,"새로운 과제가 등록 되었습니다.",tempLink);

        return assign_id;
    }

    @Transactional
    public LectureAssignmentReponseDto findLectureAssignmentInfo(Long assignment_id){

        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        LectureAssignmentReponseDto reponseDto = new LectureAssignmentReponseDto(lectureAssignment);

        return reponseDto;
    }

    @Transactional
    public Long LectureAssignmentUpdate(LectureAssignmentSaveRequestDto requestDto,Long assignment_id){

        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        lectureAssignment.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getDeadline());


        return lectureAssignment.getId();
    }

    @Transactional
    public List<LectureAssignmentSubmittedFileDto> findAllSubmitfile(Long assignment_id){

        List<LectureAssignmentSubmittedFileDto> responseDtoList = new ArrayList<LectureAssignmentSubmittedFileDto>();
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));


        for (SubmittedFile submittedFile : lectureAssignment.getSubmittedFileSet()){
            User current_student = submittedFile.getUser();
            List<FileEntity> fileList = submittedFile.getFileList();

            List<SubmittedFileResponseDto> fileResponseDtoList = fileList.stream().map(SubmittedFileResponseDto::new).collect(Collectors.toList());
            LectureAssignmentSubmittedFileDto tempResponseDto = new LectureAssignmentSubmittedFileDto(current_student.getName(),fileResponseDtoList);

            responseDtoList.add(tempResponseDto);

        }

        return responseDtoList;
    }

    @Transactional
    public boolean isDeadlineOver (Long lecture_id, Long assignment_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        return LocalDateTime.now().isAfter(lectureAssignment.getDeadline());
    }

    @Transactional
    public Long lectureUpdate(lecturesSaveRequestDto requestDto, long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));

        lecture.update(requestDto.getTitle(),requestDto.getOutline());

        return lecture_id;
    }
    @Transactional
    public Long lectureLessonUpdate(LectureLessonSaveRequestDto requestDto,Long lesson_id){

        LectureLesson lectureLesson = lectureLessonRepository.findById(lesson_id).orElseThrow(() -> new IllegalArgumentException("그런 수업은 없습니다."));

        lectureLesson.update(requestDto.getTitle(),processYoutubeLink(requestDto.getLink()));

        return lesson_id;
    }

    @Transactional
    public LectureLessonResponseDto  findPrevLesson(Long lecture_id, Long lesson_id){
        LectureLesson lectureLesson = lectureLessonRepository.findById(lesson_id).orElseThrow(() -> new IllegalArgumentException("그런 수업은 없습니다."));
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));

        Integer lessonIndex = lecture.getLectureLessons().indexOf(lectureLesson);

        if (lessonIndex == 0){
            return null;
        }
        else{
            return new LectureLessonResponseDto(lecture.getLectureLessons().get(lessonIndex-1));
        }

    }
    @Transactional
    public LectureLessonResponseDto  findNextLesson(Long lecture_id, Long lesson_id){
        LectureLesson lectureLesson = lectureLessonRepository.findById(lesson_id).orElseThrow(() -> new IllegalArgumentException("그런 수업은 없습니다."));
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));

        Integer lessonIndex = lecture.getLectureLessons().indexOf(lectureLesson);

        if (lessonIndex == lecture.getLectureLessons().size() - 1){
            return null;
        }
        else{
            return new LectureLessonResponseDto(lecture.getLectureLessons().get(lessonIndex+1));
        }

    }

    @Transactional
    public boolean isLecturer(SessionUser sessionUser, Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));

        if (lecture.getLecturer().getId() == getUserBySessionUser(sessionUser).getId()){
            System.out.println("this is lecturer!");
            return true;
        }
        else{
            System.out.println("this is another!");
            return false;
        }
    }

    @Transactional
    public boolean isAttendee (SessionUser sessionUser, Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        User user = getUserBySessionUser(sessionUser);

        for (CourseRegistration attendeesInfoItem : lecture.getCurrent_Attendees()){
            if (attendeesInfoItem.getUser().getId() == user.getId()){
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Long saveMessage (Long lecture_id, String content, String link){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        Message message = new Message(lecture,content, link);

        Long message_id = messageRepository.save(message).getId();
        for (CourseRegistration attendeesInfoItem : lecture.getCurrent_Attendees()){

            User attendee = attendeesInfoItem.getUser();
            List<Message> messageList = attendee.getMessageList();

            if (messageList.size() > 10 ){
                messageList.remove(0);
            }
            messageList.add(message);
            attendee.setIsNewAlarm(true);
        }

        return message_id;

    }

    @Transactional
    public List<NotificationListResponseDto> findUserNotification (SessionUser sessionUser){

        User user = getUserBySessionUser(sessionUser);

        List<NotificationListResponseDto> result = new ArrayList<NotificationListResponseDto>();
        List<Message> tempList= user.getMessageList();


        for (Message message : (tempList)) {
            NotificationListResponseDto temp = new NotificationListResponseDto(message);
            result.add(temp);
        }

        Collections.reverse(result);

        return result;

    }

    @Transactional
    public List<LectureAttendeeListResponseDto> findAttendeeList (Long lecture_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));

        return lecture.getCurrent_Attendees().stream().map(LectureAttendeeListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long lectureLeave(Long lecture_id, SessionUser sessionUser){

        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        User user = getUserBySessionUser(sessionUser);

        for (LectureAssignment lectureAssignment : lecture.getLectureAssignment()){

            for (SubmittedFile sf : lectureAssignment.getSubmittedFileSet()){
                if (sf.getUser() == user){
                    List<FileEntity> fileList = sf.getFileList();
                    while( fileList.isEmpty() != true){
                        FileEntity fileEntity = fileList.remove(0);
                        fileService.fileDelete(fileEntity.getId());
                    }
                    break;
                }
                else{
                    ;
                }

            }
        }

        System.out.println(user.getCurrent_Lectures().size());
        CourseRegistration tempCr = null;

        for (CourseRegistration cr : user.getCurrent_Lectures()) {
            if (cr.getLecture() == lecture) {
                tempCr = cr;
                break;
            }
        }

        user.getCurrent_Lectures().remove(tempCr);
        lecture.getCurrent_Attendees().remove(tempCr);
        courseRegistrationRepository.delete(tempCr);

        System.out.println(user.getCurrent_Lectures().size());

        System.out.println("service is finished");
        return lecture_id;

    }

    @Transactional
    public boolean isNewAlarm (SessionUser sessionUser){
        User user = getUserBySessionUser(sessionUser);

        return user.getIsNewAlarm();

    }
    @Transactional
    public Long setAlarmCheck(SessionUser sessionUser){
        User user = getUserBySessionUser(sessionUser);
        user.setIsNewAlarm(false);

        return user.getId();

    }

    @Transactional
    public Long lectureKick(Long lecture_id, Long user_id){

        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        User user = userRepository.findById(user_id).orElseThrow(()-> new IllegalArgumentException("There is no User that what you find."));

        for (LectureAssignment lectureAssignment : lecture.getLectureAssignment()){

            for (SubmittedFile sf : lectureAssignment.getSubmittedFileSet()){
                if (sf.getUser() == user){
                    List<FileEntity> fileList = sf.getFileList();
                    while( fileList.isEmpty() != true){
                        FileEntity fileEntity = fileList.remove(0);
                        fileService.fileDelete(fileEntity.getId());
                    }
                    break;
                }
                else{
                    ;
                }

            }
        }

        return lecture_id;

    }

    @Transactional
    public Long lectureNoticeUpdate( LectureNoticeSaveRequestDto requestDto, SessionUser sessionUser, Long lecture_Notice_id){

        LectureNotice lectureNotice = lectureNoticeRepository.findById(lecture_Notice_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        lectureNotice.update(requestDto);

        return lecture_Notice_id;

    }
    @Transactional
    public Long deleteLesson(Long lecture_id, Long lesson_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        LectureLesson lectureLesson = lectureLessonRepository.findById(lesson_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        lecture.getLectureLessons().remove(lectureLesson);
        System.out.println(lecture.getLectureLessons().size());
        lectureLessonRepository.delete(lectureLesson);

        return lesson_id;


    }

    @Transactional
    public Long deleteLectureNotice(Long lecture_id, Long lecture_Notice_id){
        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        LectureNotice lectureNotice = lectureNoticeRepository.findById(lecture_Notice_id).orElseThrow(()->new IllegalArgumentException("해당 강의가 없습니다."));

        lecture.getLectureLessons().remove(lectureNotice);
        lectureNoticeRepository.delete(lectureNotice);

        return lecture_Notice_id;

    }
    @Transactional
    public Long deleteLectureAssignment (Long lecture_id, Long assignment_id){

        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(()-> new IllegalArgumentException("There is no Lecture that what you find."));
        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()-> new IllegalArgumentException("There is no Assignment that what you find."));

        for (SubmittedFile sf : lectureAssignment.getSubmittedFileSet()){

            List<FileEntity> fileList = sf.getFileList();
            while( fileList.isEmpty() != true){
                FileEntity fileEntity = fileList.remove(0);
                fileService.fileDelete(fileEntity.getId());
            }

            submittedFileRepository.delete(sf);

        }

        lectureAsssignmentRepository.delete(lectureAssignment);

        return assignment_id;
    }

    @Transactional
    public boolean isAdmin(SessionUser sessionUser){
        User user = getUserBySessionUser(sessionUser);
        System.out.println("isAdmin");
        System.out.println("user.getRole() ");
        if (user.getRole() == Role.ADMIN){
            return true;
        }
        else{
            return false;
        }

    }


    private String processYoutubeLink(String link){
        String youtubeVideoCode = parsingYoutubeVideoUniqueCode(link);
        String result = "https://www.youtube.com/embed/"+youtubeVideoCode;

        System.out.println(result);
        return result;
    }
    private String parsingYoutubeVideoUniqueCode(String link){

        try {
            URL aURL = new URL(link);

            System.out.println("protocol = " + aURL.getProtocol());
            System.out.println("authority = " + aURL.getAuthority());
            System.out.println("host = " + aURL.getHost());
            System.out.println("port = " + aURL.getPort());
            System.out.println("path = " + aURL.getPath());
            System.out.println("query = " + aURL.getQuery());
            System.out.println("filename = " + aURL.getFile());
            System.out.println("ref = " + aURL.getRef());

            if (aURL.getHost().equals("youtu.be")){
                System.out.println(aURL.getPath());
                return aURL.getPath();
            }
            else{
                String query = aURL.getQuery();
                String[] queryList = query.split("&");;

                for (String queryItem : queryList){
                    String[] queryItemElements = queryItem.split("=");
                    if (queryItemElements[0].equals("v")){
                        System.out.println(queryItemElements[1]);
                        return queryItemElements[1];
                    }
                    else{ ; }
                }

            }

        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }

        return "test";
    }



    private User getUserBySessionUser(SessionUser sessionUser){
        String userEmail = sessionUser.getEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. Email ="+userEmail));

        return user;
    }
}

