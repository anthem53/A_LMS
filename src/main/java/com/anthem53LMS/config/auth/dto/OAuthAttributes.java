package com.anthem53LMS.config.auth.dto;

import com.anthem53LMS.domain.subLecturer.SubLecturer;
import com.anthem53LMS.domain.user.Role;
import com.anthem53LMS.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;



    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            System.out.println("Naver Login!");
            return ofNaver("id", attributes);
        }
        else if ("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        else{
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");

        String profile_image = (String) properties.get("profile_image");
        System.out.println(profile_image);


        System.out.println(nickname);
        System.out.println(email);

        return OAuthAttributes.builder()
                .name((String) nickname)
                .email((String) email)
                .picture(profile_image)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
/*
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();*/
    }

    public User toEntity() {

        User user =User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();

        SubLecturer subLecturer = new SubLecturer(user);
        user.setConnectEntity(subLecturer);

        return user;
    }

}
