package com.anthem53LMS.domain.notice;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;


    @Builder
    public Notice (String title, String content){
        this.title = title;
        this.content = content;
    }

}
