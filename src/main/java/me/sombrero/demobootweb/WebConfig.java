package me.sombrero.demobootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 순서를 설정하지 않으면 add한 순서대로 적용이 됨.
         */
        /*registry.addInterceptor(new GreetingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hi")
                .order(-1);*/
        registry.addInterceptor(new GreetingInterceptor());
        registry.addInterceptor(new AnotherInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
                .addResourceLocations("classpath:/mobile/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        /**
         * setCacheControl()을 추가하면
         * 이 요청에 리턴하는 리소스들은 캐시 관련 헤더가 응답 헤더에 추가된다.
         * 위 옵션은 만약 리소스가 변경되지 않았다면 10분 동안 캐싱한다.
         * 헤더에 있는 If-Modified-Since 값의 시간을 기준으로
         * 10분 안에 다시 요청을 보내면 HTTP 응답코드가 304로 나온다.
         * 304(Not Modified)는 요청된 리소스를 재전송할 필요가 없음을 나타낸다.
         * 리소스가 변경되었다면 10분이 안지났더라도 변경된 리소스를 다시 받는다.
         */
    }

}
