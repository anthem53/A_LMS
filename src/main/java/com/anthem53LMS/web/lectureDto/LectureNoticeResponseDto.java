package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureNoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public LectureNoticeResponseDto (LectureNotice lectureNotice){
        id = lectureNotice.getId();
        title = lectureNotice.getTitle();
        content = lectureNotice.getContent();
        author = lectureNotice.getLecture().getLecturer().getUser().getName();

    }
}
