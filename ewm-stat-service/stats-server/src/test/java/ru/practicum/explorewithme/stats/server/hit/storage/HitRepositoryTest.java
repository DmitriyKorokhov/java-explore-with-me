package ru.practicum.explorewithme.stats.server.hit.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class HitRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private HitRepository hitRepository;
    private Hit hit1;
    private Hit hit2;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime timeForHit1 = LocalDateTime.parse("2022-09-06 11:00:00", formatter);
    private LocalDateTime timeForHit2 = LocalDateTime.parse("2021-09-07 12:00:00", formatter);

    @BeforeEach
    private void beforeEach() {
        hit1 = hitRepository.save(new Hit(null, "ewm-main-service", "/events/1", "192.163.0.1", timeForHit1));
        entityManager.persist(hit1);

        hit2 = hitRepository.save(new Hit(null, "ewm-stat-service", "/events/56", "192.163.0.3", timeForHit2));
        entityManager.persist(hit2);

    }

    @Test
    void saveTheFirstOfTwoHitsTest() {
        assertThat(hit1.getApp().equals("ewm-main-service")).isTrue();
        assertThat(hit1.getUri().equals("/events/1")).isTrue();
        assertThat(hit1.getIp().equals("192.163.0.1")).isTrue();
        assertThat(hit1.getTimestamp().equals(LocalDateTime.of(2022, 9, 06, 11, 00, 00))).isTrue();
    }

    @Test
    void saveTheSecondOfTwoHitsTest() {
        assertThat(hit2.getApp().equals("ewm-stat-service")).isTrue();
        assertThat(hit2.getUri().equals("/events/56")).isTrue();
        assertThat(hit2.getIp().equals("192.163.0.3")).isTrue();
        assertThat(hit2.getTimestamp().equals(LocalDateTime.of(2021, 9, 07, 12, 00, 00))).isTrue();
    }
}