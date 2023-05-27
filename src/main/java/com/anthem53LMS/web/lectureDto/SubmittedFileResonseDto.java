package com.anthem53LMS.web.lectureDto;

import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;

public class SubmittedFileResonseDto {
    private Long user_id;
    private Long assignment_id;

    private Long totalSize;

    private float score;

    private boolean isGrade;

    private Long current ;

    public SubmittedFileResonseDto(SubmittedFile submittedFile){

        user_id = submittedFile.getUser().getId();
        assignment_id = submittedFile.getLectureAssignment().getId();
        totalSize = submittedFile.getTotolFileSize();
        score = submittedFile.getScore();
        isGrade = submittedFile.isGrade();
        System.out.println("[Debug ] ***  SubmittedFileResonseDto *** ");
        System.out.println(totalSize);
        System.out.println(totalSize / 20000000f);
        System.out.println(totalSize / 20000000f * 100.0f);
        System.out.println(" ***************************** ");
        current =  (long)(totalSize / 20000000f * 100.0f);



    }


}
