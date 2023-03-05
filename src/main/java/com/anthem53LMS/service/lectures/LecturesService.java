package com.anthem53LMS.service.lectures;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.Dto.LecturesResponseDto;
import com.anthem53LMS.web.Dto.lecturesListResponseDto;
import com.anthem53LMS.web.Dto.lecturesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class LecturesService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    @Transactional
    public Long lectureSave(lecturesSaveRequestDto requestDto, SessionUser sUser){

        String userEmail = sUser.getEmail();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. Email ="+userEmail));
        requestDto.setLecturer(user.getName());
        requestDto.setLecturer_user(user);

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
        Lecture entity = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new LecturesResponseDto(entity);
    }

}
