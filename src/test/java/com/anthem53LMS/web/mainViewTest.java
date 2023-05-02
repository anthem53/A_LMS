package com.anthem53LMS.web;


import com.anthem53LMS.domain.lecture.Lecture;
import com.anthem53LMS.domain.lecture.LectureRepository;
import com.anthem53LMS.domain.notice.Notice;
import com.anthem53LMS.domain.notice.NoticeRepository;
import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.Dto.LectureRegisterRequestDto;
import com.anthem53LMS.web.Dto.NoticeSaveRequestDto;
import com.anthem53LMS.web.Dto.lecturesSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class mainViewTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private Long TEST_USER_ID;
    @Autowired
    private UserRepository userRepository;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @After
    public void tearDown() throws Exception {
        noticeRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void notice_submit() throws Exception{

        String title = "테스트용 공지 입니다.";
        String content = "테스트용 공지 내용입니다.";
        NoticeSaveRequestDto requestDto =  new NoticeSaveRequestDto(title, content);

        String url = "http://localhost:" + port + "/api/v1/notice-save";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Notice> all = noticeRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }



}
