package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationListResponseDto {

    private Long id;
    private String lecture_name;
    private String content;
    private String link;
    private String modifiedDate;

    public NotificationListResponseDto(String lecture_name, String content, String link){
        this.lecture_name = lecture_name;
        this.content = content;
        this.link = link;
    }

    public NotificationListResponseDto(Message message){
        this.lecture_name = message.getLecture().getTitle();
        this.content = message.getContent();
        this.link = message.getLink();
        this.modifiedDate = message.getModifiedDate();

    }


}
