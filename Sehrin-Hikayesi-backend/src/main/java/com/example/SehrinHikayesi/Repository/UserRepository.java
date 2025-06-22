package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    long count();
    @Query("SELECT COUNT(u) FROM User u WHERE FUNCTION('DATE', u.createdAt) = CURRENT_DATE")
    long countUsersCreatedToday();
    Page<User> findAll(Pageable pageable);
    List<User> findByUsernameContainingIgnoreCase(String keyword);
    



}

