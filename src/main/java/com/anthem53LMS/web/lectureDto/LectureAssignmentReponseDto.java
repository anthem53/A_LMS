package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentReponseDto {

    private Long id;
    private String title;
    private String content;


    public LectureAssignmentReponseDto(LectureAssignment lectureAssignment){
        id = lectureAssignment.getId();
        title = lectureAssignment.getTitle();
        content  =lectureAssignment.getContent();

    }
}
