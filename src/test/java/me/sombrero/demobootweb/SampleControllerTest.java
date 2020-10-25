package me.sombrero.demobootweb;

import org.hamcrest.Matchers;
import org.hibernate.event.internal.DefaultPersistOnFlushEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// @WebMvcTest // 웹과 관련된 빈들만 등록해줌.
@SpringBootTest // 통합테스트로 변경. 웹뿐만 아니라 모든 빈들을 다 등록해줌.
@AutoConfigureMockMvc // @WebMvcTest가 아니면 MockMvc를 가져올 수 없기 때문에 또 등록해줌.
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    /*@Test
    public void hello() throws Exception {
        *//*this.mockMvc.perform(get("/hello/sombrero104"))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));*//*
        this.mockMvc.perform(get("/hello").param("name", "sombrero104"))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));
    }*/

    @Test
    public void hello() throws Exception {
        Person person = new Person();
        person.setName("sombrero104");
        Person savedPerson = personRepository.save(person);

        this.mockMvc.perform(get("/hello")
                .param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));
    }

    @Test
    public void helloStatic() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello index")));
    }

    @Test
    public void helloMobile() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Hello Mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }

    @Test
    public void stringMessage() throws Exception {
        this.mockMvc.perform(get("/message")
            .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

}