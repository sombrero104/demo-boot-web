package me.sombrero.demobootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
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
                .addResourceLocations("classpath:/mobile/") // 주로 classpath 사용.
                // .addResourceLocations("classpath:/mobile/", "file:/Users/sombrero104/files") // 파일시스템 기준으로 경로 설정도 가능.
                // .addResourceLocations("/mobile/") // classpath를 주지 않으면 디폴트로 src/main/webapp 디렉토리에서 찾음.
                // .resourceChain(true) // 캐시를 사용할지 안할지에 대한 설정. 운영중이라면 true, 개발중이라면 false로 사용하면 편함.
                /*.resourceChain(true)
                    .addResolver() // 어떤 요청에 해당하는 리소스를 찾는 방법 설정.
                    .addTransformer() // 응답으로 내보낼 리소스를 변경하는 방법 설정.*/
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

    /**
     * 컨버터 설정.
     * 주의! configureMessageConverters() 설정을 추가하면 기본 컨버터들이 사용 안됨..
     * 기본 컨버터들을 사용하면서 새로 추가만 하고 싶을 경우에는 extendMessageConverters() 사용.
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/
    /*@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/

}
