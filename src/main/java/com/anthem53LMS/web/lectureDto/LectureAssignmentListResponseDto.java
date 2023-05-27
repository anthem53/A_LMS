package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentListResponseDto {

    private Long id;
    private String title;
    private String modifiedDate;
    private String deadline;


    public LectureAssignmentListResponseDto(LectureAssignment lectureAssignment){
        id = lectureAssignment.getId();
        title = lectureAssignment.getTitle();
        modifiedDate = lectureAssignment.getModifiedDate();
        deadline = lectureAssignment.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
