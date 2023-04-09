package com.anthem53LMS.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    CustomAuthSuccessHandler(){
        return;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        clearSession(request);

        SavedRequest savedRequest = requestCache.getRequest(request,response);

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null){
            request.getSession().removeAttribute("prevPage");
        }

        // 기본 URI
        String uri = "/";

        /**
         * savedRequest 존재하는 경우 = 인증 권한이 없는 페이지 접근
         * Security Filter가 인터셉트하여 savedRequest에 세션 저장
         */
        System.out.println(prevPage);
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();
            System.out.println(1);
        } else if (prevPage != null && !prevPage.equals("")) {

            uri = prevPage;
            System.out.println(2);
            // 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
            /*if (prevPage.contains("/auth/join")) {
                uri = "/";
            } else {
                uri = prevPage;
            }*/
        }
        System.out.println(uri);
        //uri = prevPage;
        if (uri.equals("http://localhost:8080/login")){
            uri = "http://localhost:8080/";
        }
        redirectStrategy.sendRedirect(request, response, uri);


    }

    protected void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }


}
