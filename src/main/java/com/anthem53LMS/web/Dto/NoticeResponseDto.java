package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResponseDto {
    private String title;
    private String content;
    private String author;


    public NoticeResponseDto(Notice entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = "관리자";

    }

}
