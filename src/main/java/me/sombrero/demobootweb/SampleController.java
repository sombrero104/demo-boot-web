package me.sombrero.demobootweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    /**
     * PersonFormatter를 만든 후 사용.
     * 직접 만든 포매터를 WebConfig에 등록해줘도 되고 스프링부트에서는 그냥 빈으로만 등록해줘도 사용 가능하다.
     */
    /*@GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person) {
        return "hello " + person.getName();
    }*/
    @GetMapping("/hello")
    public String hello(@RequestParam("name") Person person) {
        return "hello " + person.getName();
    }

    /**
     * 스프링 데이터 JPA가 제공하는 스프링 MVC용 도메인 클래스 컨버터를 사용해보자.
     */
    @GetMapping("/hello2")
    public String hello2(@RequestParam("id") Person person) {
        return "hello2 " + person.getName();
    }

}
