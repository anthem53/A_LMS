package com.anthem53LMS.web.Dto;


import com.anthem53LMS.domain.lecture.Lecture;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class lecturesSaveRequestDto {
    private Long id;
    private String title;

    private String outline;

    private String lecturer;

    public lecturesSaveRequestDto(){

    }
    public lecturesSaveRequestDto(String title, String outline){

        this.title=title;
        this.outline=outline;

    }
    public void print(){
        System.out.println(title);
        System.out.println(outline);
    }
    public Lecture toEntity(){
        Lecture lecture = Lecture.builder()
                .title(title).outline(outline).lecturer(lecturer).build();
        return lecture;
    }

}
