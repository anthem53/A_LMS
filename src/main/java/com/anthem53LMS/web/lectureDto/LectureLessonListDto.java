package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lesson.LectureLesson;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@NoArgsConstructor
public class LectureLessonListDto {

    private Long id;
    private Integer order;
    private String title;

    private LocalDateTime modifiedDate;

    public  LectureLessonListDto (LectureLesson lectureLesson){
        id = lectureLesson.getId();
        order = lectureLesson.getSequence();
        title = lectureLesson.getTitle();
        modifiedDate = lectureLesson.getModifiedDate();
    }
}
