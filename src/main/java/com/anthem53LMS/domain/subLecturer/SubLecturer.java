package com.anthem53LMS.domain.subLecturer;

import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.user.Role;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class SubLecturer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "Lecturer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id")
    private List<Lecture> lectures = new ArrayList<Lecture>();

    @OneToOne(mappedBy = "subLecturer")
    @JoinColumn(name="User_id")
    private User user ;




    public SubLecturer(User user){

        this.user = user;


    }

    @Builder
    public SubLecturer(String name, User user){
        this.user = user;
    }


}
