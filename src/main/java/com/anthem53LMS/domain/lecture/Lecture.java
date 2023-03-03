package com.anthem53LMS.domain.lecture;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String outline;

    @Column (columnDefinition = "TEXT",nullable = false)
    private String lecturer;

    @Builder
    public Lecture (String title, String outline, String lecturer){
        this.title = title;
        this.outline = outline;
        this.lecturer = lecturer;
    }



}
