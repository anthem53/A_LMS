package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.notice.NoticeRepository;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.service.notice.NoticeService;
import com.anthem53LMS.web.lectureDto.LectureLessonSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/test")
    public String test(Model model, @LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        return "test";
    }
    @GetMapping("/test/notice")
    public String test_notice(Model model, @LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        return "lecture/lecture-notice";
    }
    @GetMapping("/test/notice/register")
    public String test_notice_register(Model model, @LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        return "lecture/lecture-notice-register";
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
    public String lecture_register_show(Model model, @LoginUser SessionUser user){

        if ( user == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",user.getName());
            System.out.println("User");
        }

        model.addAttribute("lectures",lecturesService.findAttendedLecture(user));


        return "lecture-register";
    }

    @GetMapping("/showLecture/register/take_course/{id}")
    public String lecture_take (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            System.out.println("User");
        }

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));
        model.addAttribute("lecture_notice", lecturesService.findLectureNotice(id));

        return "lecture/lecture-home";
    }

    @GetMapping("/showLecture/register/take_course/{id}/notice")
    public String lecture_notice (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            System.out.println("User");
        }

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));
        model.addAttribute("lecture_notice", lecturesService.findLectureNotice(id));

        return "lecture/lecture-notice";
    }

    @GetMapping("/showLecture/register/take_course/{id}/notice/save")
    public String lecture_notice_save (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            System.out.println("User");
        }

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));

        return "lecture/lecture-notice-save";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/notice/{notice_id}")
    public String LectureNoticeInquiry(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long notice_id ){
        System.out.println("LectureNoticeInquiry");

        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            System.out.println("User");
        }

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(lecture_id));
        model.addAttribute("lecture_notice",lecturesService.findLectureNoticeInfo(lecture_id,notice_id));

        return "lecture/lecture-notice-inquiry";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson")
    public String lecture_lesson_list (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id ){
        System.out.println("lecture_lesson_list");

        setUserInfo(model, sessionUser);
        System.out.println("lecture_lesson_list call user");
        model.addAttribute("lecture_lesson",lecturesService.findLectureLesson(lecture_id));
        setLectureInfo(model,lecture_id);

        return "lecture/lecture-lesson";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson/save")
    public String lecture_lesson_save (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);

        return "lecture/lecture-lesson-save";
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





    private void setUserInfo(Model model, SessionUser sessionUser){
        if ( sessionUser == null){
            System.out.println("guest");
        }
        else{
            model.addAttribute("userName",sessionUser.getName());
            System.out.println("User");
        }

    }

    private void setLectureInfo (Model model,Long lecture_id ){
        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(lecture_id));
    }


}
