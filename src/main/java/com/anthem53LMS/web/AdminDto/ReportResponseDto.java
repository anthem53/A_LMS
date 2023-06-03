package com.anthem53LMS.web.AdminDto;


import com.anthem53LMS.domain.report.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportResponseDto {

    private Long id;
    private String reporterName;
    private Long reporterId;
    private String link;

    private String content;

    public ReportResponseDto(Report report){
        id = report.getId();
        reporterName = report.getReporter().getName();
        reporterId = report.getReporter().getId();
        link = report.getLink();
        content = report.getContent();
    }


}
