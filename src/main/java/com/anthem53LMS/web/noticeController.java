package com.anthem53LMS.web;

import com.anthem53LMS.service.notice.NoticeService;
import com.anthem53LMS.web.Dto.NoticeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class noticeController {

    private final NoticeService noticeService;

    @PostMapping("/api/v1/notice-save")
    public Long notice_save (@RequestBody NoticeSaveRequestDto requestDto){

        System.out.println("Start notice-save");
        Long notice_id = noticeService.save(requestDto);

        System.out.println("end notice-save");
        return notice_id;
    }

    @PostMapping("/api/v1/notice-update/{notice_id}")
    public Long notice_update (@RequestBody NoticeSaveRequestDto requestDto , @PathVariable Long notice_id){


        return noticeService.update(requestDto,notice_id);
    }


}


