package com.anthem53LMS.web;

import com.anthem53LMS.service.admin.AdminService;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class adminAPIController {

    private final AdminService adminService;


    @PostMapping("/api/v1/admin/userInfo/{user_id}")
    public UserListResponseDto getUserInfo(@PathVariable Long user_id){

        return adminService.findById(user_id);
    }


}
