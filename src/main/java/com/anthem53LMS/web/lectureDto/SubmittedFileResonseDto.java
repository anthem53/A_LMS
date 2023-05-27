package com.anthem53LMS.web.lectureDto;

import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;

public class SubmittedFileResonseDto {
    private Long user_id;
    private Long assignment_id;

    private String totalSize;

    private float score;

    private boolean isGrade;

    private Long current ;

    public SubmittedFileResonseDto(SubmittedFile submittedFile){

        user_id = submittedFile.getUser().getId();
        assignment_id = submittedFile.getLectureAssignment().getId();
        totalSize = get_current_size(submittedFile.getTotolFileSize());
        score = submittedFile.getScore();
        isGrade = submittedFile.isGrade();
        current =  (long)(submittedFile.getTotolFileSize() / 20000000f * 100.0f);
        System.out .println (current);
    }

    private String get_current_size(Long tempTotalSize){

        String current_size = "";
        if (tempTotalSize >= 1000000){
            float temp = (float) tempTotalSize / 1000000f;
            System.out.println(temp);
            current_size = String.format("%.2f",temp) + "MB";
        }
        else if (tempTotalSize >= 1000){
            float temp = (float) tempTotalSize / 1000f;
            System.out.println(temp);
            current_size = String.format("%.2f",temp) + "KB";

        }
        else {
            current_size = String.valueOf(tempTotalSize) + "B";
        }
        return current_size;
    }



}
