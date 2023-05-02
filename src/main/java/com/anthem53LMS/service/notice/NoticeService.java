package com.anthem53LMS.service.notice;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.notice.Notice;
import com.anthem53LMS.domain.notice.NoticeRepository;
import com.anthem53LMS.web.Dto.LecturesResponseDto;
import com.anthem53LMS.web.Dto.NoticeResponseDto;
import com.anthem53LMS.web.Dto.NoticeSaveRequestDto;
import com.anthem53LMS.web.Dto.NoticesListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(NoticeSaveRequestDto requestDto){

        Notice notice = requestDto.toEntity();

        return noticeRepository.save(notice).getId();
    }

    @Transactional(readOnly = true)
    public List<NoticesListResponseDto> findAllDesc(){

        return noticeRepository.findAllDesc().stream().map(NoticesListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public NoticeResponseDto findById(Long id) {
        Notice entity = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new NoticeResponseDto(entity);
    }

    @Transactional
    public Long update(NoticeSaveRequestDto requestDto,Long notice_id){
        Notice notice = noticeRepository.findById(notice_id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        notice.update(requestDto.getTitle(),requestDto.getContent());


        return notice.getId();
    }

    @Transactional
    public Long delete(Long notice_id){
        Notice notice = noticeRepository.findById(notice_id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        noticeRepository.delete(notice);

        return notice_id;

    }
}
