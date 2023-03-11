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
    private String videoLink;


    public LectureLessonResponseDto (LectureLesson lectureLesson){
        title = lectureLesson.getTitle();
        videoLink = lectureLesson.getVideoLink();

    }
}
