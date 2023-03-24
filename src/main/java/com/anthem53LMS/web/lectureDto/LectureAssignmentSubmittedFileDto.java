package com.anthem53LMS.web.lectureDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LectureAssignmentSubmittedFileDto {

    private  String name;

    private List<SubmittedFileResponseDto> submittedFile;

    public LectureAssignmentSubmittedFileDto(String name,List<SubmittedFileResponseDto> submittedFile ){

        this.name = name;
        this.submittedFile = submittedFile;
    }

}
