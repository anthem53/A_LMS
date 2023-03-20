package com.anthem53LMS.domain.studentAssignInfo;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AssignmentCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lectureAssignment_id")
    private LectureAssignment lectureAssignment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isCompleted ;

    public AssignmentCheck(LectureAssignment lectureAssignment, User attendee){
        this.lectureAssignment  = lectureAssignment;
        this.user = attendee;
        isCompleted = false;

    }



}
