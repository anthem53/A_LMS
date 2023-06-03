package com.anthem53LMS.web;

import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.service.admin.AdminService;
import com.anthem53LMS.service.lectures.LecturesService;
import com.anthem53LMS.web.AdminDto.ReportResponseDto;
import com.anthem53LMS.web.AdminDto.ReportSavedRequestDto;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import com.anthem53LMS.web.Dto.LecturesResponseDto;
import com.anthem53LMS.web.lectureDto.LectureNoticeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        LecturesResponseDto temp = lecturesService.findById(lecture_id);

        return temp;

    }

    @PostMapping("/api/v1/report")
    public Long sendReportMessage(@RequestBody ReportSavedRequestDto respondDto, @LoginUser SessionUser sessionUser){
        System.out.println("Call sendReportMessage");

        return adminService.saveReport(respondDto,sessionUser);

    }

    @PostMapping("/api/v1/admin/reportInfo/{report_id}")
    public ReportResponseDto getReportInfo(@PathVariable Long report_id){

        return adminService.findReportById(report_id);
    }


}
