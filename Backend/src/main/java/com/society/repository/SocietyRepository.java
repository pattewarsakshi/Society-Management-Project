package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.society.entity.Society;

public interface SocietyRepository extends JpaRepository<Society, Integer> {

    // Optional (useful later)
    boolean existsBySocietyName(String societyName);
}
