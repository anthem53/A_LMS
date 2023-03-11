package com.anthem53LMS.domain.lecture;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.courceRegistration.CourseRegistration;
import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @OneToMany(mappedBy = "lecture")
    Set<CourseRegistration> current_Attendees = new HashSet<CourseRegistration>();

    @OneToMany(mappedBy = "lecture")
    List<LectureNotice> lectureNotices  = new ArrayList<LectureNotice>();



    @Builder
    public Lecture (String title, String outline, String lecturer, SubLecturer subLecturer ){
        this.title = title;
        this.outline = outline;
        this.Lecturer = subLecturer;
    }



}
