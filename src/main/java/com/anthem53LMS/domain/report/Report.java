package com.anthem53LMS.domain.report;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reporter;

    private String link;

    private String content;

    private boolean isDone;

    @Builder
    public Report(User reporter, String link, String content){

        this.reporter = reporter;
        this.link = link;
        this.content  = content;
        this.isDone = false;
    }
}
