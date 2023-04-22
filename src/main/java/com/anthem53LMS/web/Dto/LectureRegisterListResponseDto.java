package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.courseRegistration.CourseRegistration;
import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureRegisterListResponseDto {

    private Long id;
    private String title;
    private String Lecturer;
    private String modifiedDate;


    public LectureRegisterListResponseDto (CourseRegistration courseRegistration){
        Lecture lecture = courseRegistration.getLecture();

        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.Lecturer = lecture.getLecturer().getUser().getName();
        this.modifiedDate = lecture.getModifiedDate();

    }

    public LectureRegisterListResponseDto (Lecture lecture){

        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.Lecturer = lecture.getLecturer().getUser().getName();
        this.modifiedDate = lecture.getModifiedDate();

    }

}
