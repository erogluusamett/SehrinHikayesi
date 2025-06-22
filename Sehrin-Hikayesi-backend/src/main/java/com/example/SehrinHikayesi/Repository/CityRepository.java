package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c.id, c.name, COUNT(s.id) " +
            "FROM City c LEFT JOIN Story s ON s.city.id = c.id " +
            "GROUP BY c.id, c.name")
    List<Object[]> getCitiesWithStoryCount();
    List<City> findByNameContainingIgnoreCase(String keyword);


}


