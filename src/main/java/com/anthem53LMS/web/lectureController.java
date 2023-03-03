package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.web.Dto.lecturesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class lectureController {

    private final LecturesService lecturesService;
    @PostMapping("/api/v1/lecture-save")
    public Long save (@RequestBody lecturesSaveRequestDto requestDto , @LoginUser SessionUser user){

        System.out.println("Call lecture save ");
        requestDto.print();

        Long temp = lecturesService.lectureSave(requestDto,user);
        return 1l;
    }

}
