package com.anthem53LMS.domain.lecture_assignment;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.lecture.Lecture;
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

    private LocalDateTime deadline;

    @Builder
    public LectureAssignment(String title, String content, Lecture lecture, LocalDateTime deadline){
        this.title = title;
        this.content = content;
        this.lecture = lecture;
        this.deadline = deadline;
    }

    public void update(String title, String content,String deadline){
        String[] raw = deadline.split(" ");
        String[] days= raw[0].split("-");
        String[] times = raw[1].split(":");
        int year = Integer.parseInt(days[0]);
        int  month = Integer.parseInt(days[1]);
        int  day = Integer.parseInt(days[2]);

        int  hour = Integer.parseInt(times[0]);
        int  minute = Integer.parseInt(times[1]);
        int  second = 0;

        LocalDateTime newDeadline = LocalDateTime.of(year,month,day,hour,minute,second);

        this.title = title;
        this.content = content;
        this.deadline = newDeadline;
    }

}
