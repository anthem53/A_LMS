package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class lecturesListResponseDto {
    private Long id;
    private String title;
    private SubLecturer lecturer;
    //private String lecturer;
    private LocalDateTime modifiedDate;

    public lecturesListResponseDto(Lecture lecture){

        this.id = lecture.getId();
        this.title= lecture.getTitle();
        this.lecturer = lecture.getLecturer();
        //this.lecturer = "subLecturer 파트 구현해야함.";
        this.modifiedDate = lecture.getModifiedDate();


    }



}

