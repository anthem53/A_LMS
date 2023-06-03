package com.anthem53LMS.web.AdminDto;


import com.anthem53LMS.domain.report.Report;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReportListResponseDto {

    private Long id;
    private User reporter;
    private String link;
    private String content;

    public ReportListResponseDto(Report report){
        id = report.getId();
        reporter = report.getReporter();
        link = report.getLink();
        content = report.getContent();

    }

}
