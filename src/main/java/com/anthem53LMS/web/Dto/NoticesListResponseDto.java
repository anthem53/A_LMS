package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticesListResponseDto {
    private Long id;
    private String title;
    private String lecturer;
    private String modifiedDate;

    public NoticesListResponseDto(Notice notice){

        this.id = notice.getId();
        this.title= notice.getTitle();
        this.lecturer = "관리자";
        this.modifiedDate = notice.getModifiedDate();

    }



}
