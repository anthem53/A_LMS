package com.anthem53LMS.web;

import com.anthem53LMS.service.admin.AdminService;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import com.anthem53LMS.web.Dto.LecturesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class adminAPIController {

    private final AdminService adminService;

    private final LecturesService lecturesService;

    @PostMapping("/api/v1/admin/userInfo/{user_id}")
    public UserListResponseDto getUserInfo(@PathVariable Long user_id){

        return adminService.findById(user_id);
    }

    @PostMapping("/api/v1/admin/lectureInfo/{lecture_id}")
    public LecturesResponseDto getLectureInfo(@PathVariable Long lecture_id){
        System.out.println("*****************");
        System.out.println(lecture_id);
        System.out.println("*****************");

        LecturesResponseDto temp = lecturesService.findById(lecture_id);

        System.out.println("*****************");
        System.out.println(temp.getTitle());
        System.out.println("*****************");

        return temp;

    }


}
