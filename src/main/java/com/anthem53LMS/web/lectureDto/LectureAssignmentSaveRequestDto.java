package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentSaveRequestDto {

    private String title;
    private String content;
    private Lecture lecture;
    private String deadline;

    public LectureAssignmentSaveRequestDto(String title, String content, String deadline){
        this.title = title;
        this.content = content;
        this.deadline = deadline;

    }

    public LectureAssignment toEntity(){

        String[] raw = this.deadline.split(" ");
        String[] days= raw[0].split("-");
        String[] times = raw[1].split(":");
        int year = Integer.parseInt(days[0]);
        int  month = Integer.parseInt(days[1]);
        int  day = Integer.parseInt(days[2]);

        int  hour = Integer.parseInt(times[0]);
        int  minute = Integer.parseInt(times[1]);
        int  second = 0;

        LocalDateTime deadlineTime = LocalDateTime.of(year,month,day,hour,minute,second);
        //LocalDateTime dateOfBirth = LocalDateTime.of(1982, 7, 13, 14, 25, 00);
        LectureAssignment lectureAssignment = LectureAssignment.builder().title(title).content(content).lecture(lecture).deadline(deadlineTime).build();

        return lectureAssignment;
    }

}


