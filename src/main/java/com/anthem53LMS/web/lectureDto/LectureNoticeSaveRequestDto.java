package com.anthem53LMS.web.lectureDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureNoticeSaveRequestDto {
    private Long id;
    private String title;
    private String content;

    public LectureNoticeSaveRequestDto(String title, String content){

        this.title = title;
        this.content = content;

    }
}
