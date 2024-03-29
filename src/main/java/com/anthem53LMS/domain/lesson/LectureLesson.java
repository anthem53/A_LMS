package com.anthem53LMS.domain.lesson;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class LectureLesson extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer sequence;

    @Column
    private String videoLink;

    @ManyToOne
    @JoinColumn(name="Lecture_id")
    private Lecture lecture = null;


    @Builder
    public LectureLesson(Integer sequence, String videoLink, String title){
        this.sequence = sequence;
        this.videoLink = videoLink;
        this.title = title;
    }

    public void setLecture(Lecture lecture){
        this.lecture = lecture;
    }
    public void setSequence(Integer sequenceValue) { this.sequence = sequenceValue;}


    public void update(String title, String link){
        this.title = title;
        this.videoLink = link;

    }



}
