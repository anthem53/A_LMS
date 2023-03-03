package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequestDto {

    private Long id;
    private String title;
    private String content;

    public NoticeRequestDto(String title, String content){
        this.title= title;
        this.content = content;
    }

    public Notice toEntity(){
        Notice notice = Notice.builder().title(title).content(content).build();

        return notice;

    }

}
