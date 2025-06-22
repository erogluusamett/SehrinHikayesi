package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCityId(Long cityId);

    @Query("SELECT c FROM Category c LEFT JOIN c.stories s GROUP BY c ORDER BY COUNT(s.id) DESC")
    List<Category> findTopCategories(Pageable pageable);

    default List<Category> findTopCategories(int limit) {
        return findTopCategories(PageRequest.of(0, limit));
    }

    List<Category> findByNameContainingIgnoreCase(String name);

    boolean existsByCityId(Long cityId);
}

