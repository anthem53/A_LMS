package com.anthem53LMS.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate_Raw;
    private String createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate_Raw;

    private String modifiedDate;



    @PrePersist
    public void onPrePersist(){
        this.createDate = createDate_Raw.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createDate;

//        this.createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//        this.modifiedDate = this.createDate;

    }

    @PostUpdate
    public void onPostUpdate(){

        this.modifiedDate = modifiedDate_Raw.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

    }



}
