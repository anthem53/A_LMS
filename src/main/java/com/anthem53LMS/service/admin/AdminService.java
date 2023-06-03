package com.anthem53LMS.service.admin;


import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.domain.report.Report;
import com.anthem53LMS.domain.report.ReportRepository;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.AdminDto.ReportListResponseDto;
import com.anthem53LMS.web.AdminDto.ReportResponseDto;
import com.anthem53LMS.web.AdminDto.ReportSavedRequestDto;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    @Transactional(readOnly = true)
    public List<UserListResponseDto> findAllUserInfo(){


        return userRepository.findAllDesc().stream().map(UserListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserListResponseDto findById(Long user_id){

        User user = userRepository.findById(user_id).orElseThrow( () -> new IllegalArgumentException(" 해당 유저는 없습니다. "));

        return new UserListResponseDto(user);

    }

    @Transactional
    public Long saveReport(ReportSavedRequestDto requestDto, SessionUser sessionUser){
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(()-> new IllegalArgumentException("There is no User Who you want"));
        requestDto.setReporter(user);

        Report report = requestDto.toEntity();

        return reportRepository.save(report).getId();


    }
    @Transactional
    public ReportResponseDto findReportById(Long report_id){
        Report report = reportRepository.findById(report_id).orElseThrow(()->new IllegalArgumentException("There is no report that has report_id"));


        return new ReportResponseDto(report);
    }

    @Transactional
    public List<ReportListResponseDto> findAllReportList(){

        List<ReportListResponseDto> temp = new ArrayList<ReportListResponseDto>();

        for (Report report : reportRepository.findAllAsc()){
            temp.add(new ReportListResponseDto(report));

        }

        return temp;
    }
    @Transactional
    public List<ReportListResponseDto> findUndoneReportList(){

        List<ReportListResponseDto> temp = new ArrayList<ReportListResponseDto>();

        for (Report report : reportRepository.findAllAsc()){
            if (report.isDone() != true){
                temp.add(new ReportListResponseDto(report));
            }
        }

        return temp;
    }
    @Transactional
    public List<ReportListResponseDto> findDoneReportList(){

        List<ReportListResponseDto> temp = new ArrayList<ReportListResponseDto>();

        for (Report report : reportRepository.findAllAsc()){
            if (report.isDone() == true){
                temp.add(new ReportListResponseDto(report));
            }
        }

        return temp;
    }
}
