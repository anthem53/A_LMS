package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentSaveRequestDto {

    private String title;
    private String content;
    private Lecture lecture;

    public LectureAssignmentSaveRequestDto(String title, String content){
        this.title = title;
        this.content = content;

    }

    public LectureAssignment toEntity(){
        LectureAssignment lectureAssignment = LectureAssignment.builder().title(title).content(content).lecture(lecture).build();

        return lectureAssignment;
    }
}
