package ru.practicum.explorewithme.stats.server.stats.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.stats.dto.StatsDto;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;
import ru.practicum.explorewithme.stats.server.hit.storage.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class StatsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private HitRepository hitRepository;
    private Hit hit1;
    private Hit hit2;
    private Hit hit3;
    private Hit hit4;
    private Hit hit5;
    private List<String> uris = Arrays.asList("/events/1");
    private boolean unique = false;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime start = LocalDateTime.parse("2019-09-06 11:00:00", formatter);
    private final LocalDateTime end = LocalDateTime.parse("2030-09-06 11:00:00", formatter);
    private final LocalDateTime newStartFuture = LocalDateTime.parse("2045-09-06 11:00:00", formatter);
    private final LocalDateTime newEndFuture = LocalDateTime.parse("2045-09-06 11:00:00", formatter);
    private final LocalDateTime newStartPast = LocalDateTime.parse("1987-09-06 11:00:00", formatter);
    private final LocalDateTime newEndPast = LocalDateTime.parse("1987-09-06 11:00:00", formatter);
    private LocalDateTime timeForHit1 = LocalDateTime.parse("2022-09-06 11:00:00", formatter);
    private LocalDateTime timeForHit2 = LocalDateTime.parse("2022-09-07 12:00:00", formatter);
    private LocalDateTime timeForHit3 = LocalDateTime.parse("2022-09-08 13:00:00", formatter);
    private LocalDateTime timeForHit4 = LocalDateTime.parse("2023-03-03 07:00:00", formatter);
    private LocalDateTime timeForHit5 = LocalDateTime.parse("2023-03-06 09:00:00", formatter);

    @BeforeEach
    private void beforeEach() {
        hit1 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", timeForHit1));
        entityManager.persist(hit1);

        hit2 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", timeForHit2));
        entityManager.persist(hit2);

        hit3 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", timeForHit3));
        entityManager.persist(hit3);

        hit4 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.100.100", timeForHit4));
        entityManager.persist(hit4);

        hit5 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/56", "192.163.120.120", timeForHit5));
        entityManager.persist(hit5);
    }

    @Test
    void findByDateTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(4L)
                .build();
        StatsDto statsDto2 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .hits(1L)
                .build();
        int size = 2;
        List<StatsDto> stats = statsRepository.findByDate(start, end);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
        assertThat(stats.contains(statsDto2)).isTrue();
    }

    @Test
    void findByDateFutureTest() {
        List<StatsDto> stats = statsRepository.findByDate(newStartFuture, newEndFuture);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDatePastTest() {
        List<StatsDto> stats = statsRepository.findByDate(newStartPast, newEndPast);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUniqueIpTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
        StatsDto statsDto2 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/56")
                .hits(1L)
                .build();
        int size = 2;
        List<StatsDto> stats = statsRepository.findByDateAndUniqueIp(start, end);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
        assertThat(stats.contains(statsDto2)).isTrue();
    }

    @Test
    void findByDateAndUniqueIpFutureTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUniqueIp(newStartFuture, newEndFuture);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUniqueIpPastTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUniqueIp(newStartPast, newEndPast);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUrisTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(4L)
                .build();
        int size = 1;
        List<StatsDto> stats = statsRepository.findByDateAndUris(start, end, uris);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
    }

    @Test
    void findByDateAndUrisFutureTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUris(newStartFuture, newEndFuture, uris);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUrisPastTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUris(newStartPast, newEndPast, uris);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUrisWithUniqueIpTest() {
        StatsDto statsDto1 = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
        int size = 1;
        List<StatsDto> stats = statsRepository.findByDateAndUrisWithUniqueIp(start, end, uris);
        assertThat(stats.size() == size).isTrue();
        assertThat(stats.contains(statsDto1)).isTrue();
    }

    @Test
    void findByDateAndUrisWithUniqueIpFutureTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUrisWithUniqueIp(newStartFuture, newEndFuture, uris);
        assertThat(stats.isEmpty()).isTrue();
    }

    @Test
    void findByDateAndUrisWithUniqueIpPastTest() {
        List<StatsDto> stats = statsRepository.findByDateAndUrisWithUniqueIp(newStartPast, newEndPast, uris);
        assertThat(stats.isEmpty()).isTrue();
    }
}