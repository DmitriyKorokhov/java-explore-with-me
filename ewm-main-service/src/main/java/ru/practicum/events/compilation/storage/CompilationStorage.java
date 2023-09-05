package ru.practicum.events.compilation.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.compilation.model.Compilation;

import java.util.List;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation, Long> {
    @Query("SELECT c FROM Compilation AS c " +
            "WHERE c.pinned = :pinned OR :pinned IS NULL")
    List<Compilation> findAllByPinnedIs(Boolean pinned, Pageable pageable);
}