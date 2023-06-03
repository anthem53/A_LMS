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


    @GetMapping("/admin")
    public String admin(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "user");

        model.addAttribute("user",adminService.findAllUserInfo());

        return "admin/main";
    }

    @GetMapping("/admin/user")
    public String adminUser(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "user");

        model.addAttribute("user",adminService.findAllUserInfo());

        return "admin/main";
    }

    @GetMapping("/admin/request")
    public String adminRequest(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "open");

        model.addAttribute("user",adminService.findAllUserInfo());

        return "admin/main";
    }

    @GetMapping("/admin/lecture")
    public String adminLecture(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "lecture");

        model.addAttribute("lecture",lecturesService.findAllDesc());

        return "admin/main";
    }


    @GetMapping("/admin/report/undone")
    public String adminReportUndone(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "report");

        setReportTabType(model, "undone");


        return "admin/main";
    }

    @GetMapping("/admin/report/all")
    public String adminReportAll(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "report");

        setReportTabType(model, "all");


        return "admin/main";
    }

    @GetMapping("/admin/report/done")
    public String adminReportDone(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);
        setTabType(model, "report");

        setReportTabType(model, "done");


        return "admin/main";
    }


    private void setReportTabType(Model model, String tab_type){
        if (tab_type == "all"){
            model.addAttribute("report",adminService.findAllReportList());
        }
        else if (tab_type == "done"){
            model.addAttribute("report",adminService.findDoneReportList());
        }
        else{
            model.addAttribute("report",adminService.findUndoneReportList());
        }


    }

    private void setTabType(Model model, String tab_type){

        if (tab_type.equals("user")){
            model.addAttribute("type_user",true);
        }
        else if (tab_type.equals("open")){
            model.addAttribute("type_open",true);
        }
        else if (tab_type.equals("lecture")){
            model.addAttribute("type_lecture",true);
        }
        else if (tab_type.equals("report")){
            model.addAttribute("type_report",true);
        }
        else{
            return;
        }


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
