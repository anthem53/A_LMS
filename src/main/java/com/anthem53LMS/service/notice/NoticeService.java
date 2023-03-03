package com.anthem53LMS.service.notice;


import com.anthem53LMS.domain.notice.Notice;
import com.anthem53LMS.domain.notice.NoticeRepository;
import com.anthem53LMS.web.Dto.NoticeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(NoticeRequestDto requestDto){

        Notice notice = requestDto.toEntity();

        return noticeRepository.save(notice).getId();
    }
}
