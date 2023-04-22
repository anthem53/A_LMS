package com.anthem53LMS.domain.message;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String link;


    @ManyToOne
    private Lecture lecture;


    public Message(Lecture lecture, String content, String link){
        this.lecture = lecture;
        this.content =content;
        this.link = link;

    }

    private void setContent(String content){
        this.content =content;
    }

    private void setLink(String link){
        this.link = link;
    }

}
