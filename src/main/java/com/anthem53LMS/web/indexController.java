package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.notice.NoticeRepository;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.service.notice.NoticeService;
import com.anthem53LMS.web.Dto.lecturesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class indexController {

    private final LecturesService lecturesService;
    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String index(Model model ,@LoginUser SessionUser user){

        System.out.println("index!!");

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        model.addAttribute("lectures",lecturesService.findAllDesc());
        model.addAttribute("notices",noticeService.findAllDesc());


        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @LoginUser SessionUser user, HttpServletRequest request){

        System.out.println("loginPage!!");

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        System.out.println(uri);


        return "auth/login";

    }

    @GetMapping("/showLecture")
    public String lecture(Model model ,@LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        model.addAttribute("lectures",lecturesService.findAllDesc());


        return "lecture-show";
    }

    @GetMapping("/openLecture")
    public String lecture_open(Model model ,@LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }


        return "lecture-open";
    }

    @GetMapping("/showLecture/inquiry/{id}")
    public String lecture_inquiry(Model model, @LoginUser SessionUser user , @PathVariable Long id){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        model.addAttribute("lecture",lecturesService.findById(id));
        model.addAttribute("lectureId",id);


        return "lecture-inquiry";

    }

    @GetMapping("/showLecture/register")
    public String lecture_register_show(Model model, @LoginUser SessionUser){



        return "lecture-register";
    }

    @GetMapping("/notice")
    public String notice(Model model, @LoginUser SessionUser user){
        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        model.addAttribute("notices",noticeService.findAllDesc());

        return "notice";

    }

    @GetMapping("/notice/post")
    public String notice_post(Model model, @LoginUser SessionUser user){
        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        return "notice-post";


    }

    @GetMapping("/notice/inquiry/{id}")
    public String noticeInquiry(Model model, @LoginUser SessionUser user, @PathVariable Long id) {

        model.addAttribute("notice",noticeService.findById(id));
        System.out.println(id);

        return "notice-inquiry";
    }

}
