package ru.practicum.explorewithme.stats.server.hit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

}