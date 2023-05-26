package com.anthem53LMS.domain.file;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class FileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String originalName;
    @Column
    private String uuid;
    @Column
    private String savedPath;

    @ManyToOne
    private SubmittedFile submittedFile;

    private Long fileSize ;
    @Builder
    public FileEntity(Long id , String rawName, String uuid, String savedPath, Long fileSize){

        this.id = id;
        this.originalName = rawName;
        this.uuid = uuid;
        this.savedPath = savedPath;
        this.fileSize = fileSize;

    }

    public void setSubmittedFile(SubmittedFile submittedFile){
        this.submittedFile =submittedFile;
    }




}
