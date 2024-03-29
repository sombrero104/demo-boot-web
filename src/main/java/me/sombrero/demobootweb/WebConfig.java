package me.sombrero.demobootweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.*;

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
     * 하지만 주로 이 설정을 사용할 일이 많지 않다.
     * 주로 컨버터를 새로 추가할 때에는 의존성을 추가해서 사용할 일이 많다.
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/
    /*@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
        /**
         * Person에 애노테이션 @XmlRootElement를 설정해서 JAXB에게 알려줌.
         */
        return jaxb2Marshaller;
    }

    /**
     * "/hi"라는 요청이 들어오면 "hi"에 해당하는 뷰를 리턴하도록 뷰 컨트롤러를 등록.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hi").setViewName("hi");
    }

    /**
     * 비동기 관련 설정.
     * 비동기 처리할 때 사용하는 TaskExecutor에 스레드풀 개수가 몇개인지 설정하거나 타임아웃 설정.
     */
    /*@Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }*/

    /**
     * 요청 본문 또는 응답 본문을 어떤 (MIME) 타입으로 보내야 하는지 결정하는 전략을 설정한다.
     * 기본 전략은 요청 헤더에 들어있는 Accept나 Content-Type을 보고 판단한다.
     * 그런데 이렇게 헤더 값을 주기 어려운 경우, url에 확장자를 쓰고 싶은 경우,
     * (http://localhost:8080/hello.json)
     * 이런 설정을 configureContentNegotiation()에서 설정할 수 있다.
     */
    /*@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }*/
}
