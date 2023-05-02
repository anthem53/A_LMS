package com.anthem53LMS.domain.lecture_notice;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.web.lectureDto.LectureNoticeSaveRequestDto;
import com.anthem53LMS.web.lectureDto.LectureTakeViewRespondDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LectureNotice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="Lecture_id")
    private Lecture lecture = null;


    @Builder
    public LectureNotice(String title, String content, Lecture lecture){
        this.title = title;
        this.content = content;
        this.lecture = lecture;
    }

    public LectureNotice(LectureNoticeSaveRequestDto respondDto){
        this.title = respondDto.getTitle();
        this.content = respondDto.getContent();

    }

    public void setLecture(Lecture lecture){
        this.lecture = lecture;

    }

    public void update(LectureNoticeSaveRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }

}
