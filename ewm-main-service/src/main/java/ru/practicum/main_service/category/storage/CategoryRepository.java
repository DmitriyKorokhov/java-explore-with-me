package ru.practicum.main_service.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main_service.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("FROM Category AS C " +
            "WHERE LOWER(C.name) LIKE LOWER(:name) " +
            "AND C.id NOT IN (:excludeCatId)")
    Category findByName(String name, Long excludeCatId);

}