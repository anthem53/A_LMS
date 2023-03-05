package com.anthem53LMS.domain.lecture;

import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

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

    @Column (columnDefinition = "TEXT",nullable = false)
    private String lecturer;

    @ManyToOne
    @JoinColumn(name="User_id")
    private User user;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id")
    private List<User> students;

    @Builder
    public Lecture (String title, String outline, String lecturer, User user){
        this.title = title;
        this.outline = outline;
        this.lecturer = lecturer;
        this.user = user;
    }



}
