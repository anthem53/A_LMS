package com.anthem53LMS.domain.lecture_assignment;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.studentAssignInfo.AssignmentCheck;
import com.anthem53LMS.domain.supportDomain.submitFile.SubmittedFile;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Entity
public class LectureAssignment extends BaseTimeEntity {

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


    @OneToMany(mappedBy = "lectureAssignment", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<SubmittedFile> submittedFileSet = new HashSet<SubmittedFile>();

    private boolean isDeadlineOver = false;

    //private LocalDateTime deadline;

    @Builder
    public LectureAssignment(String title, String content, Lecture lecture){
        this.title = title;
        this.content = content;
        this.lecture = lecture;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
