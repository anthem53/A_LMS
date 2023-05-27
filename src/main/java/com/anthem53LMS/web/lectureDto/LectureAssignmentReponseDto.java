package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentReponseDto {

    private Long id;
    private String title;
    private String content;

    private String deadline;
    private String deadlineDate;
    private String deadlineTime;



    public LectureAssignmentReponseDto(LectureAssignment lectureAssignment){
        LocalDateTime temp = lectureAssignment.getDeadline();

        id = lectureAssignment.getId();
        title = lectureAssignment.getTitle();
        content  =lectureAssignment.getContent();
        deadline = String.valueOf(temp.getYear())+"-"+String.format("%02d", temp.getMonthValue())+"-"+String.valueOf(temp.getDayOfMonth())+" "+String.format("%02d", temp.getHour())+":"+String.format("%02d", temp.getMinute());
        deadlineDate = String.valueOf(temp.getYear())+"-"+String.format("%02d", temp.getMonthValue())+"-"+String.valueOf(temp.getDayOfMonth());
        deadlineTime = String.format("%02d", temp.getHour())+":"+String.format("%02d", temp.getMinute());




    }
}
