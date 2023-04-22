package com.anthem53LMS.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    //private LocalDateTime createDate;
    private String createDate;

    @LastModifiedDate
    private String modifiedDate;
    //private LocalDateTime modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }



}
