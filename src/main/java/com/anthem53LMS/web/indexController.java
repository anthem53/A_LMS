package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.service.file.FileService;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.service.notice.NoticeService;
import com.anthem53LMS.web.Dto.NotificationListResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class indexController {

    private final LecturesService lecturesService;
    private final FileService fileService;
    private final NoticeService noticeService;


    @GetMapping("/")
    public String index(Model model ,@LoginUser SessionUser sessionUser){

        System.out.println("index!!");

        setUserInfo(model, sessionUser);

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
    public String lecture(Model model ,@LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);

        model.addAttribute("lectures",lecturesService.findAllDesc());
        model.addAttribute("isUser", sessionUser != null);


        return "lecture-show";
    }

    @GetMapping("/notification")
    public String notification(Model model , @LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);

        lecturesService.setAlarmCheck(sessionUser);
        List<NotificationListResponseDto> temp =lecturesService.findUserNotification(sessionUser);
        model.addAttribute("messageList", temp);

        return "notification";
    }
    @GetMapping("/openLecture")
    public String lecture_open(Model model ,@LoginUser SessionUser sessionUser){

        setUserInfo(model, sessionUser);

        return "lecture-open";
    }

    @GetMapping("/showLecture/inquiry/{id}")
    public String lecture_inquiry(Model model, @LoginUser SessionUser sessionUser , @PathVariable Long id){

        setLectureInfo(model, id);
        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,id);


        return "lecture-inquiry";

    }

    @GetMapping("/showLecture/register")
    public String lecture_register_show(Model model, @LoginUser SessionUser user){

        setUserInfo(model, user);

        model.addAttribute("lectures",lecturesService.findAttendedLecture(user));


        return "lecture-register";
    }

    @GetMapping("/showLecture/register/take_course/{id}")
    public String lecture_take (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,id);
        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));
        model.addAttribute("lecture_notice", lecturesService.findLectureNotice(id));
        model.addAttribute("isHome",true);

        return "lecture/lecture-home";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/introduction")
    public String lecture_introduction(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){

        setLectureInfo(model,lecture_id);
        setUserInfo(model,sessionUser);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("isIntro",true);

        return "lecture/lecture-introduction";
    }

    //showLecture/register/take_course/1/attendeeList
    @GetMapping("/showLecture/register/take_course/{lecture_id}/attendeeList")
    public String lecture_attendee(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){

        setLectureInfo(model,lecture_id);
        setUserInfo(model,sessionUser);
        setLecturerAttendee(model, sessionUser,lecture_id);
        model.addAttribute("attendee",lecturesService.findAttendeeList(lecture_id));

        return "lecture/lecture-attendee";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/change")
    public String lecture_change(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);


        return "lecture-update";
    }
    @GetMapping("/showLecture/register/take_course/{id}/notice")
    public String lecture_notice (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,id);

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));
        model.addAttribute("lecture_notice", lecturesService.findLectureNotice(id));

        model.addAttribute("isNotice",true);
        return "lecture/lecture-notice";
    }

    @GetMapping("/showLecture/register/take_course/{id}/notice/save")
    public String lecture_notice_save (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){
        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,id);

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(id));
        model.addAttribute("isNotice",true);
        return "lecture/lecture-notice-save";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/notice/{notice_id}")
    public String LectureNoticeInquiry(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long notice_id ){

        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(lecture_id));
        model.addAttribute("lecture_notice",lecturesService.findLectureNoticeInfo(lecture_id,notice_id));

        model.addAttribute("isNotice",true);

        return "lecture/lecture-notice-inquiry";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/notice/{notice_id}/update")
    public String LectureNoticeUpdate(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long notice_id ){

        setUserInfo(model, sessionUser);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(lecture_id));
        model.addAttribute("lecture_notice",lecturesService.findLectureNoticeInfo(lecture_id,notice_id));

        model.addAttribute("isNotice",true);

        return "lecture/lecture-notice-update";
    }



    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson")
    public String lecture_lesson_list (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id ){
        System.out.println("lecture_lesson_list");

        setUserInfo(model, sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        System.out.println("lecture_lesson_list call user");
        model.addAttribute("lecture_lesson",lecturesService.findLectureLesson(lecture_id));
        model.addAttribute("isLecturer",lecturesService.isLecturer(sessionUser,lecture_id));
        model.addAttribute("isLesson",true);

        return "lecture/lecture-lesson";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson/{lesson_id}")
    public String lecture_lesson_inquiry (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long lesson_id){
        System.out.println("lecture_lesson_inquiry");
        setUserInfo(model, sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("lesson",lecturesService.findLessonInfo(lecture_id,lesson_id));
        model.addAttribute("prevLesson",lecturesService.findPrevLesson(lecture_id,lesson_id));
        model.addAttribute("nextLesson",lecturesService.findNextLesson(lecture_id,lesson_id));


        model.addAttribute("isLesson",true);


        return "lecture/lecture-lesson-inquiry";

    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson/save")
    public String lecture_lesson_save (Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("isLesson",true);

        return "lecture/lecture-lesson-save";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/lesson/{lesson_id}/update")
    public String lecture_lesson_update(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id, @PathVariable Long lesson_id){
        setUserInfo(model, sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("lesson",lecturesService.findLessonInfo(lecture_id,lesson_id));
        model.addAttribute("isLesson",true);

        return "lecture/lecture-lesson-update";

    }


    @GetMapping("/showLecture/register/take_course/{lecture_id}/assignment")
    public String lecture_assignment(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("lecture_assignment", lecturesService.findLectureAssignment(lecture_id));
        model.addAttribute("isLecturer",lecturesService.isLecturer(sessionUser,lecture_id));
        model.addAttribute("isAssignment",true);


        return "lecture/lecture-assignment";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/assignment/save")
    public String lecture_assignment_save(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id){
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        model.addAttribute("isAssignment",true);
        return "lecture/lecture-assignment-save";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/assignment/{assignment_id}")
    public String lecture_assignment_inquiry(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long assignment_id){

        setLecturerAttendee(model, sessionUser,lecture_id);
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setIsDeadlineOver(model,lecture_id,assignment_id);

        model.addAttribute("lectureAssignment",lecturesService.findLectureAssignmentInfo(assignment_id));
        model.addAttribute("fileList",fileService.findSubmittedFileList(assignment_id,sessionUser));
        model.addAttribute("score", lecturesService.getAssignmentScore(assignment_id,sessionUser));
        model.addAttribute("isGrade", lecturesService.isSubmitedAssignment(assignment_id,sessionUser));
        model.addAttribute("submittedFile",lecturesService.findSubmittedFile(assignment_id,sessionUser));

        model.addAttribute("isAssignment",true);

        return "lecture/lecture-assignment-inquiry";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/assignment/{assignment_id}/change")
    public String lecture_assignment_change(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long assignment_id){

        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);
        setLecturerAttendee(model, sessionUser,lecture_id);

        //LectureAssignmentReponseDto
        model.addAttribute("lectureAssignment",lecturesService.findLectureAssignmentInfo(assignment_id));
        model.addAttribute("isAssignment",true);

        return "lecture/lecture-assignment-update";
    }

    @GetMapping("/showLecture/register/take_course/{lecture_id}/assignment/{assignment_id}/submittedList")
    public String lecture_assignment_submit_list(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long lecture_id , @PathVariable Long assignment_id){

        setLecturerAttendee(model, sessionUser,lecture_id);
        setUserInfo(model,sessionUser);
        setLectureInfo(model,lecture_id);

        model.addAttribute("lectureAssignment",lecturesService.findLectureAssignmentInfo(assignment_id));
        model.addAttribute("attendee",lecturesService.findAllSubmitfile(assignment_id));

        model.addAttribute("isAssignment",true);

        return "lecture/lecture-assignment-submitted-file";
    }

    @GetMapping("/showOwnLecture")
    public String lecture_own(Model model, @LoginUser SessionUser sessionUser){

        setUserInfo(model,sessionUser);
        model.addAttribute("lectures", lecturesService.findAllOwnLecture(sessionUser));

        return "lecture-own";
    }

    @GetMapping("/notice")
    public String notice(Model model, @LoginUser SessionUser user){
        setUserInfo(model,user);

        model.addAttribute("notices",noticeService.findAllDesc());

        return "notice";

    }

    @GetMapping("/notice/post")
    public String notice_post(Model model, @LoginUser SessionUser user){
        setUserInfo(model,user);

        return "notice-post";


    }
    @GetMapping("/notice/update/{id}")
    public String notice_update(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id){

        setUserInfo(model, sessionUser);

        model.addAttribute("notice",noticeService.findById(id));

        return "notice-update";
    }

    @GetMapping("/notice/inquiry/{id}")
    public String noticeInquiry(Model model, @LoginUser SessionUser sessionUser, @PathVariable Long id) {

        setUserInfo(model, sessionUser);
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
            model.addAttribute("userPicture",sessionUser.getPicture());
            model.addAttribute("isNewAlarm", lecturesService.isNewAlarm(sessionUser));
            model.addAttribute("isAdmin",lecturesService.isAdmin(sessionUser));
            System.out.println("User");

        }

    }

    private void setLectureInfo (Model model,Long lecture_id ){
        //LectureTakeViewRespondDto
        model.addAttribute("lecture", lecturesService.findLectureTitleAndContent(lecture_id));
    }

    private void setLecturerAttendee(Model model, SessionUser sessionUser, Long lecture_id){
        model.addAttribute("isLecturer",lecturesService.isLecturer(sessionUser,lecture_id));
        model.addAttribute("isAttendee",lecturesService.isAttendee(sessionUser,lecture_id));
        model.addAttribute("isGuest", sessionUser == null);
    }

    private void setIsDeadlineOver(Model model, Long lecture_id, Long assignment_id){
        model.addAttribute("isDeadlineOver",lecturesService.isDeadlineOver(lecture_id,assignment_id));

    }


}
