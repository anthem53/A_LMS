package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lesson.LectureLesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LectureLessonSaveRequestDto {
    private Long id;
    private String title;
    private Integer order;
    private String link;


    public LectureLessonSaveRequestDto (String title, String link){
        this.title = title;
        this.link = link;

    }

    public LectureLesson toEntity(){
        LectureLesson lectureLesson = LectureLesson.builder().title(title).sequence(order).videoLink(link).build();

        return lectureLesson;
    }

}
