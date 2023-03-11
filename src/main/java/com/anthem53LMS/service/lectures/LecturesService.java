package com.anthem53LMS.service.lectures;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.courceRegistration.CourseRegistration;
import com.anthem53LMS.domain.courceRegistration.CourseRegistrationRepository;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import com.anthem53LMS.domain.lecture_notice.LectureNoticeRepository;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.Dto.*;
import com.anthem53LMS.web.lectureDto.LectureNoticeListResponseDto;
import com.anthem53LMS.web.lectureDto.LectureNoticeSaveRequestDto;
import com.anthem53LMS.web.lectureDto.LectureTakeViewRespondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class LecturesService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureNoticeRepository lectureNoticeRepository;

    private final CourseRegistrationRepository courseRegistrationRepository;
    @Transactional
    public Long lectureSave(lecturesSaveRequestDto requestDto, SessionUser sUser){

        String userEmail = sUser.getEmail();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. Email ="+userEmail));

        requestDto.setSubLecturer(user.getSubLecturer());


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

}
