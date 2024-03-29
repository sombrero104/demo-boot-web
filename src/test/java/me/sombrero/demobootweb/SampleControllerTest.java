package me.sombrero.demobootweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hibernate.event.internal.DefaultPersistOnFlushEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

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

    @Autowired
    ObjectMapper objectMapper; // JSON

    @Test
    public void jsonMessage() throws Exception {
        Person person = new Person();
        person.setId(2019l);
        person.setName("sombrero104");

        String jsonString = objectMapper.writeValueAsString(person);

        /**
         * 어떤 컨버터를 사용할 것인지를 요청의 헤더에 있는 Content-Type 정보를 보고 판단한다.
         */
        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청 헤더 Content-Type을 json으로 준다.
                .accept(MediaType.APPLICATION_JSON_UTF8) // 응답으로 어떠한 타입을 원하는지 알려주는 것.
                .content(jsonString))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2019))
            .andExpect(jsonPath("$.name").value("sombrero104"));
    }

    @Autowired
    Marshaller marshaller; // XML

    /**
     * 스프링이 추상화한 인터페이스.
     * WebConfig에서 우리가 등록한 Jaxb2Marshaller가 이 인터페이스를 구현하고 있다.
     */

    @Test
    public void xmlMessage() throws Exception {
        Person person = new Person();
        person.setId(2019l);
        person.setName("sombrero104");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        /**
         * 어떤 컨버터를 사용할 것인지를 요청의 헤더에 있는 Content-Type 정보를 보고 판단한다.
         */
        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_XML) // 요청 헤더 Content-Type을 json으로 준다.
                .accept(MediaType.APPLICATION_XML) // 응답으로 어떠한 타입을 원하는지 알려주는 것.
                .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/id").string("2019"))
                .andExpect(xpath("person/name").string("sombrero104"));
    }

}