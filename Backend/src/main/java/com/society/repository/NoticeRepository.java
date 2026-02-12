package com.society.repository;

import com.society.entity.Notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findByPostedBy_Society_SocietyIdOrderByCreatedAtDesc(Integer societyId);
}
