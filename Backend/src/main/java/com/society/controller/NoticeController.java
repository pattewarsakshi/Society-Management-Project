package com.society.controller;

import com.society.dto.NoticeRequestDto;
import com.society.dto.NoticeResponseDto;
import com.society.service.NoticeService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeResponseDto> createNotice(
            @RequestBody NoticeRequestDto dto,
            HttpSession session) {

        return ResponseEntity.ok(noticeService.createNotice(dto, session));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getNotices(
            HttpSession session) {

        return ResponseEntity.ok(noticeService.getNotices(session));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> updateNotice(
            @PathVariable Integer id,
            @RequestBody NoticeRequestDto dto,
            HttpSession session) {

        return ResponseEntity.ok(noticeService.updateNotice(id, dto, session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(
            @PathVariable Integer id,
            HttpSession session) {

        noticeService.deleteNotice(id, session);
        return ResponseEntity.noContent().build();
    }
}
