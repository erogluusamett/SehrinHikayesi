package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    boolean existsByCategoryId(Long id);
    boolean existsByCityId(Long cityId);
    boolean existsByLocationId(Long locationId);
    List<Story> findByCityId(Long cityId);

    @Query("SELECT COUNT(s) FROM Story s WHERE FUNCTION('DATE', s.createdAt) = CURRENT_DATE")
    long countStoriesCreatedToday();
    Page<Story> findAll(Pageable pageable);
    List<Story> findAllByAuthor(User user);
    List<Story> findByTitleContainingIgnoreCase(String keyword);
    List<Story> findByAuthorUsername(String username);
    @Query("SELECT s FROM Story s JOIN s.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :tagName))")
    List<Story> findByTagNameContainsIgnoreCase(@Param("tagName") String tagName);
    List<Story> findByCategory_NameIgnoreCase(String categoryName);






}
