package com.anthem53LMS.config.auth;



import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOAuth2UserService;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/",  "/loginPage","login","/showLecture","/showLecture/inquiry/*","/notice","/notice/inquiry/*").permitAll() // 설정된 url은 인증되지 않더라도 누구든 접근 가능
                .antMatchers("/test","/search").permitAll() // 설정된 url은 인증되지 않더라도 누구든 접근 가능
                .antMatchers("/notice/post", "/notice/update/*", "/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                .loginPage("/loginPage")
                .successHandler(customAuthSuccessHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);


    }
}
