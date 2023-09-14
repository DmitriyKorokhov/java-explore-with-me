package ru.practicum.main_service.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main_service.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("FROM Category AS c " +
            "WHERE LOWER(c.name) LIKE LOWER(:name) " +
            "AND c.id NOT IN (:excludeCatId)")
    Category findByName(@Param("name") String name, @Param("excludeCatId") Long excludeCatId);
}