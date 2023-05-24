package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentSubmittedFileDto {

    private User student;
    private List<SubmittedFileResponseDto> submittedFile;

    private float curScore;

    public LectureAssignmentSubmittedFileDto(User student,List<SubmittedFileResponseDto> submittedFile,float curScore){

        this.student = student;
        this.submittedFile = submittedFile;
        this.curScore = curScore;

    }

}
