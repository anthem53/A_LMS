package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.web.Dto.LectureRegisterRequestDto;
import com.anthem53LMS.web.Dto.lecturesSaveRequestDto;
import com.anthem53LMS.web.lectureDto.LectureLessonSaveRequestDto;
import com.anthem53LMS.web.lectureDto.LectureNoticeSaveRequestDto;
import com.anthem53LMS.web.lectureDto.LectureTakeViewRespondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@RequiredArgsConstructor
@RestController
public class lectureController {

    private final LecturesService lecturesService;

    @PostMapping("/api/v1/lecture-save")
    public Long save(@RequestBody lecturesSaveRequestDto requestDto, @LoginUser SessionUser user) {

        System.out.println("Call lecture save ");
        requestDto.print();

        Long temp = lecturesService.lectureSave(requestDto, user);
        return temp;
    }

    @PostMapping("/api/v1/lecture_register")
    public Long register(@RequestBody LectureRegisterRequestDto requestDto, @LoginUser SessionUser user) {
        System.out.println("call lecture register");


        Long id = lecturesService.LectureRegister(requestDto, user);
        return id;

    }

    @PostMapping("/api/v1/lecture/{id}/notice/save")
    public Long register_lecture_notice(@PathVariable Long id, @RequestBody LectureNoticeSaveRequestDto respondDto, @LoginUser SessionUser sessionUser) {
        System.out.println("call register_lecture_notice");

        Long temp = lecturesService.LectureNoticeSave(respondDto, id);

        return id;
    }

    @PostMapping(" /api/v1/lecture/{lecture_id}/lesson/save")
    public Long save_lecture_lesson(@PathVariable Long lecture_id, @RequestBody LectureLessonSaveRequestDto requestDto, @LoginUser SessionUser sessionUser) {
        System.out.println("call register_lecture_notice");

        Long id = lecturesService.LectureLessonSave(requestDto, lecture_id);
        return id;
    }
}