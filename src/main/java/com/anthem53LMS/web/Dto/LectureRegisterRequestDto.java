package com.anthem53LMS.web.Dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureRegisterRequestDto {

    private Long Lecture_id;

    public LectureRegisterRequestDto (Long Lecture_id){

        this.Lecture_id = Lecture_id;
    }

}
