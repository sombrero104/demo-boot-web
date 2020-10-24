package me.sombrero.demobootweb;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GreetingInterceptor와 AnotherInterceptor를 만든 후
 * WebConfig에 addInterceptors()를 추가하고 실행해보면 아래와 같이 출력이 된다.
 *
 * ##### preHandle 1
 * ##### preHandle 2
 * ##### hello sombrero104
 * ##### postHandle 2
 * ##### postHandle 1
 * ##### afterCompletion 2
 * ##### afterCompletion 1
 */
public class AnotherInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("##### preHandle 2");
        return true; // 실제 핸들러까지 다음 요청을 계속 처리하도록 true. (false는 여기에서 끝낸다는 뜻.)
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("##### postHandle 2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("##### afterCompletion 2");
    }

}
