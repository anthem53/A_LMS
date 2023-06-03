package com.anthem53LMS.web.AdminDto;


import com.anthem53LMS.domain.report.Report;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReportSavedRequestDto {
    User reporter;
    String link;
    String content;

    public ReportSavedRequestDto(String link, String content){
        this.link = link;
        this.content = content;
    }

    public Report toEntity(){
        Report report = Report.builder().reporter(reporter).link(link).content(content).build();

        return report;
    }

}
