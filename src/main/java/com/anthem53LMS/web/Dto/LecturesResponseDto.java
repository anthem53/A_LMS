package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturesResponseDto {
    private String title;
    private String outline;
    private String lecturer;


    public LecturesResponseDto(Lecture entity){
        this.title = entity.getTitle();
        this.outline = entity.getOutline();
        this.lecturer = entity.getLecturer();

    }

}
