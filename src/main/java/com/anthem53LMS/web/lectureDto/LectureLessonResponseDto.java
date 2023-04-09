package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lesson.LectureLesson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureLessonResponseDto {

    private String title;
    private String link;
    private Long id;


    public LectureLessonResponseDto (LectureLesson lectureLesson){
        title = lectureLesson.getTitle();
        link = lectureLesson.getVideoLink();
        id= lectureLesson.getId();

    }
}
