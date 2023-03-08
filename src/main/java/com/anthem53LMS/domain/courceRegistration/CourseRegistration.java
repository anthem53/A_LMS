package com.anthem53LMS.domain.courceRegistration;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CourseRegistration(Lecture l , User u){

        this.lecture = l ;
        this.user = u;

    }



}
