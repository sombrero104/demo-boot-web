package me.sombrero.demobootweb;

import org.springframework.web.bind.annotation.*;

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
    /*@GetMapping("/hello")
    public String hello(@RequestParam("name") Person person) {
        return "hello " + person.getName();
    }*/

    /**
     * 스프링 데이터 JPA가 제공하는 스프링 MVC용 도메인 클래스 컨버터를 사용해보자.
     */
    @GetMapping("/hello")
    public String hello(@RequestParam("id") Person person) {
        System.out.println("##### hello " + person.getName());
        return "hello " + person.getName();
    }

    @GetMapping("/message")
    public String message(@RequestBody String body) {
        return body;
    }

    @GetMapping("/jsonMessage")
    public Person jsonMessage(@RequestBody Person person) {
        return person;
    }

}
