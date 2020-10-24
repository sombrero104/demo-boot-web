package me.sombrero.demobootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}
