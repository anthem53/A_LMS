package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class lecturesListResponseDto {
    private Long id;
    private String title;
    private String lecturer;
    private LocalDateTime modifiedDate;

    public lecturesListResponseDto(Lecture lecture){

        this.id = lecture.getId();
        this.title= lecture.getTitle();
        this.lecturer = lecture.getLecturer();
        this.modifiedDate = lecture.getModifiedDate();

    }



}
