package com.anthem53LMS.domain.lecture;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.courseRegistration.CourseRegistration;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import com.anthem53LMS.domain.lesson.LectureLesson;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

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


    @ManyToOne
    @JoinColumn(name="User_id")
    private SubLecturer Lecturer;


    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    Set<CourseRegistration> current_Attendees = new HashSet<CourseRegistration>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE )
    List<LectureNotice> lectureNotices  = new ArrayList<LectureNotice>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    List<LectureLesson> lectureLessons = new ArrayList<LectureLesson>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    List<LectureAssignment> lectureAssignment = new ArrayList<LectureAssignment>();



    @Builder
    public Lecture (String title, String outline, String lecturer, SubLecturer subLecturer ){
        this.title = title;
        this.outline = outline;
        this.Lecturer = subLecturer;
    }


    public void update(String title, String outline){
        this.title = title;
        this.outline = outline;
    }

}
