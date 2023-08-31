package ru.practicum.explorewithme.stats.server.hit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.service.HitServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HitController.class)
@AutoConfigureMockMvc
class HitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    HitServiceImpl hitService;
    private HitDto hitDto;

    @BeforeEach
    private void beforeEach() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
    }

    @Test
    void addHitValidHitDtoTest() {
        try {
            mockMvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(hitDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHitNotAppValidHitDtoTest() {
        hitDto.setApp("");
        try {
            mockMvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(hitDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHitNotUriValidHitDtoTest() {
        hitDto.setUri("");
        try {
            mockMvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(hitDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHitNotIpValidHitDtoTest() {
        hitDto.setIp("");
        try {
            mockMvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(hitDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHitNotDateValidHitDtoTest() {
        hitDto.setTimestamp("");
        try {
            mockMvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(hitDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}