package com.anthem53LMS.domain.supportDomain.submitFile;


import com.anthem53LMS.domain.file.FileEntity;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture_assignment.LectureAssignment;
import com.anthem53LMS.domain.user.User;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class SubmittedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private LectureAssignment lectureAssignment;


    @OneToMany(mappedBy = "submittedFile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FileEntity> fileList = new ArrayList<FileEntity>();

    private float score = 0.0f;

    public SubmittedFile(LectureAssignment lectureAssignment , User user){

        this.lectureAssignment = lectureAssignment ;
        this.user = user;

    }


}
