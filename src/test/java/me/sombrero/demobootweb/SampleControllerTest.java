package me.sombrero.demobootweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
// @WebMvcTest // 웹과 관련된 빈들만 등록해줌.
@SpringBootTest // 통합테스트로 변경. 웹뿐만 아니라 모든 빈들을 다 등록해줌.
@AutoConfigureMockMvc // @WebMvcTest가 아니면 MockMvc를 가져올 수 없기 때문에 또 등록해줌.
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        /*this.mockMvc.perform(get("/hello/sombrero104"))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));*/
        this.mockMvc.perform(get("/hello").param("name", "sombrero104"))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));
    }

    @Test
    public void hello2() throws Exception {
        this.mockMvc.perform(get("/hello2").param("id", "1"))
                .andDo(print())
                .andExpect(content().string("hello sombrero104"));
    }

}