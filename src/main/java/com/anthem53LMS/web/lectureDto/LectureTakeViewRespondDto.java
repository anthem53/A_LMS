package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureTakeViewRespondDto {

    private Long id;
    private String title;
    private String lecturer;
    private String picture;
    private String outline;

    private String modifiedDate;

    public LectureTakeViewRespondDto(Lecture lecture){
        id = lecture.getId();
        title = lecture.getTitle();
        lecturer = lecture.getLecturer().getUser().getName();
        picture = lecture.getLecturer().getUser().getPicture();
        outline = lecture.getOutline();
        modifiedDate = lecture.getModifiedDate();
    }

    public void print(){
        System.out.println(id);
        System.out.println(title);
        System.out.println(lecturer);
        System.out.println(picture);
        System.out.println(outline);

    }

}
