package com.anthem53LMS.service.admin;


import com.anthem53LMS.domain.user.User;
import com.anthem53LMS.domain.user.UserRepository;
import com.anthem53LMS.web.AdminDto.UserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public List<UserListResponseDto> findAllUserInfo(){


        return userRepository.findAllDesc().stream().map(UserListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserListResponseDto findById(Long user_id){

        User user = userRepository.findById(user_id).orElseThrow( () -> new IllegalArgumentException(" 해당 유저는 없습니다. "));

        return new UserListResponseDto(user);

    }
}
