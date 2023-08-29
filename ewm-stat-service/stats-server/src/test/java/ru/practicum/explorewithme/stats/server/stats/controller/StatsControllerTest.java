package ru.practicum.explorewithme.stats.server.stats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.explorewithme.stats.dto.StatsDto;
import ru.practicum.explorewithme.stats.server.hit.service.HitServiceImpl;
import ru.practicum.explorewithme.stats.server.stats.service.StatsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
@AutoConfigureMockMvc
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    StatsServiceImpl statsService;
    @MockBean
    HitServiceImpl hitService;
    private List<StatsDto> stats = new ArrayList<>();
    private StatsDto statsDto1;
    private StatsDto statsDto2;

    @BeforeEach
    void init() {
        statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(1L)
                .build();
        statsDto2 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .hits(1L)
                .build();
        stats.add(statsDto1);
        stats.add(statsDto2);
    }

    @Test
    @SneakyThrows
    void getStatsValidRequestTest() {
        when(statsService.getStats(any(), any(), any(), anyBoolean())).thenReturn(stats);
        String result = mockMvc.perform(get("/stats")
                        .param("start", "2020-09-06 11:00:00")
                        .param("end", "2036-09-06 12:00:00")
                        .param("uris", "/events")
                        .param("unique", "true"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(statsService, times(1)).getStats(any(), any(), any(), anyBoolean());
        assertEquals(objectMapper.writeValueAsString(stats), result);
    }

    @Test
    @SneakyThrows
    void getStatsRequestNotUniqueTest() {
        when(statsService.getStats(any(), any(), any(), anyBoolean())).thenReturn(stats);
        String result = mockMvc.perform(get("/stats")
                        .param("start", "2020-09-06 11:00:00")
                        .param("end", "2036-09-06 12:00:00")
                        .param("uris", "/events"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(statsService, times(1)).getStats(any(), any(), any(), anyBoolean());
        assertEquals(objectMapper.writeValueAsString(stats), result);
    }

    @Test
    @SneakyThrows
    void getStatsRequestNotListTest() {
        when(statsService.getStats(any(), any(), any(), anyBoolean())).thenReturn(stats);
        String result = mockMvc.perform(get("/stats")
                        .param("start", "2020-09-06 11:00:00")
                        .param("end", "2036-09-06 12:00:00"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(statsService, times(1)).getStats(any(), any(), any(), anyBoolean());
        assertEquals(objectMapper.writeValueAsString(stats), result);
    }
}