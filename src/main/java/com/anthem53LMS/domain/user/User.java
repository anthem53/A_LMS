package com.anthem53LMS.domain.user;


import com.anthem53LMS.domain.BaseTimeEntity;
import com.anthem53LMS.domain.attendee.AttendeeList;
import com.anthem53LMS.domain.courceRegistration.CourseRegistration;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private SubLecturer subLecturer = null;

    @OneToMany(mappedBy = "user")
    Set<CourseRegistration> current_Lectures = new HashSet<CourseRegistration>();


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email,String picture,Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public void setConnectEntity(SubLecturer subLecturer){
        this.subLecturer = subLecturer;

    }

}
