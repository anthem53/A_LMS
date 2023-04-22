package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.courseRegistration.CourseRegistration;
import com.anthem53LMS.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureAttendeeListResponseDto {

    private Long id;
    private String picture;
    private String name;

    public LectureAttendeeListResponseDto(CourseRegistration cr){
        User user = cr.getUser();
        id = user.getId();
        picture = user.getPicture();
        name = user.getName();

    }

}
