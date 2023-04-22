package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.lecture_notice.LectureNotice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureNoticeListResponseDto {

    private Long id;
    private String title;
    private String author;
    private String modifiedDate;

    public LectureNoticeListResponseDto (LectureNotice lectureNotice){
        this.id = lectureNotice.getId();
        this.title = lectureNotice.getTitle();
        this.modifiedDate = lectureNotice.getModifiedDate();
        this.author = lectureNotice.getLecture().getLecturer().getUser().getName();

    }
}
