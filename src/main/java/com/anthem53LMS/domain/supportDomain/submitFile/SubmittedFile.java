package com.anthem53LMS.domain.supportDomain.submitFile;


import com.anthem53LMS.domain.file.FileEntity;

import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class SubmittedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private LectureAssignment lectureAssignment;


    @OneToMany(mappedBy = "submittedFile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FileEntity> fileList = new ArrayList<FileEntity>();

    private Long totolFileSize;

    private float score;
    private boolean isGrade;

    public SubmittedFile(LectureAssignment lectureAssignment , User user){

        this.lectureAssignment = lectureAssignment ;
        this.user = user;
        this.score = 0.0f;
        this.isGrade = false;
        this.totolFileSize = 0l;

    }


    public void setScore(float score){

        this.score = score;
        this.isGrade = true;
    }

    public void addFileSize(Long tempFileSize){
        this.totolFileSize += tempFileSize;
    }

    public void subFileSize(Long tempFileSize){
        this.totolFileSize -= tempFileSize;
    }
    public void setFileSize(Long fileSize){
        this.totolFileSize = fileSize;
    }

}
