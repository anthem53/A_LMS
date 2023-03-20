package com.anthem53LMS.service.lectures;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.courceRegistration.CourseRegistration;
import com.anthem53LMS.domain.courceRegistration.CourseRegistrationRepository;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.lecture_assignment.LectureAsssignmentRepository;
import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import com.anthem53LMS.domain.lecture_notice.LectureNoticeRepository;
import com.anthem53LMS.domain.lesson.LectureLesson;
import com.anthem53LMS.domain.lesson.LectureLessonRepository;
import com.anthem53LMS.domain.studentAssignInfo.AssignmentCheck;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.Dto.*;
import com.anthem53LMS.web.lectureDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class LecturesService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureNoticeRepository lectureNoticeRepository;
    private final LectureLessonRepository lectureLessonRepository;
    private final LectureAsssignmentRepository lectureAsssignmentRepository;



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

        return lectureNoticeRepository.save(lectureNotice).getId();

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


        return lectureLessonRepository.save(lectureLesson).getId();
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

        LectureAssignment lectureAssignment = requestDto.toEntity();

        Set<CourseRegistration> attendeesInfoSet = lecture.getCurrent_Attendees();

        for (CourseRegistration attendeesInfoItem : attendeesInfoSet){
            User target = attendeesInfoItem.getUser();
            AssignmentCheck assignmentCheck = new AssignmentCheck(lectureAssignment,target);

            target.getCurrent_Assignment().add(assignmentCheck);
            lectureAssignment.getAttendee().add(assignmentCheck);

        }

        return lectureAsssignmentRepository.save(lectureAssignment).getId();
    }

    @Transactional
    public LectureAssignmentReponseDto findLectureAssignmentInfo(Long assignment_id){

        LectureAssignment lectureAssignment = lectureAsssignmentRepository.findById(assignment_id).orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다."));

        LectureAssignmentReponseDto reponseDto = new LectureAssignmentReponseDto(lectureAssignment);

        return reponseDto;
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
}
