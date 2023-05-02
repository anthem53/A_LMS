package com.anthem53LMS.config;



import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Component
public class TestUserResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        boolean isTestUserAnnotation = parameter.getParameterAnnotation(TestUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isTestUserAnnotation && isUserClass;

    }


    @Override
    public Object resolveArgument (MethodParameter parameter, ModelAndViewContainer mavContainter , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{

        return httpSession.getAttribute("user");
    }

}
