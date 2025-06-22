/*
package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // LikeRepository.java
    boolean existsByUserId(Long userId);

}
*/
package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndStoryId(Long userId, Long storyId);
    void deleteByUserIdAndStoryId(Long userId, Long storyId);
    long countByStoryId(Long storyId);
    List<Like> findByStoryId(Long storyId);


}

