package com.anthem53LMS.domain.lesson;


import com.anthem53LMS.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Lesson extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "lesson order",nullable = false)
    private Integer order;

    @Column(columnDefinition = "lesson video")
    private String lesson_video_Link;


    @Builder
    public Lesson (Integer order, String lesson_video_Link){
        this.order = order;
        this.lesson_video_Link =lesson_video_Link;
    }





}
