package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.service.admin.AdminService;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.Session;

@Controller
@RequiredArgsConstructor
public class adminController {

    final private LecturesService lecturesService;
    final private AdminService adminService;
    private final UserRepository userRepository;

    @GetMapping("/admin")
    public String adminUser(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);

        model.addAttribute("user",adminService.findAllUserInfo());

        return "admin/user";
    }

    private void setUserInfo(Model model, SessionUser sessionUser){
        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            model.addAttribute("userPicture",sessionUser.getPicture());
            //model.addAttribute("isNewAlarm", lecturesService.isNewAlarm(sessionUser));
            System.out.println("User");

        }

    }




}
