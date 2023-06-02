package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturesResponseDto {

    private Long id;
    private String title;
    private String outline;
    private String lecturer;
    private Long lecturer_id;

    private Long attendeeNum;


    public LecturesResponseDto(Lecture entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.outline = entity.getOutline();
        this.lecturer = entity.getLecturer().getUser().getName();
        this.lecturer_id = entity.getLecturer().getUser().getId();
        this.attendeeNum = Long.valueOf(entity.getCurrent_Attendees().size());

    }

}
