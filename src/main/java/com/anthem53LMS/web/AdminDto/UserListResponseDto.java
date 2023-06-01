package com.anthem53LMS.web.AdminDto;


import com.anthem53LMS.domain.courseRegistration.CourseRegistration;
import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.subLecturer.SubLecturer;
import com.anthem53LMS.domain.user.Role;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserListResponseDto {


    private Long id;

    private String name;

    private String email;

    private String picture;

    private List<String> lecture_offering= new ArrayList<String>();
    private List<String> lecture_submit= new ArrayList<String>();

    private Role role;

    public UserListResponseDto (User user ){
        this.id= user.getId();
        name = user.getName();
        email = user.getEmail();
        picture = user.getPicture();

        for (Lecture lecture : user.getSubLecturer().getLectures()){
            lecture_offering.add(lecture.getTitle());
        }
        for (CourseRegistration courseRegistration : user.getCurrent_Lectures()){
            lecture_offering.add(courseRegistration.getLecture().getTitle());
        }

        role = user.getRole();
    }


}
