package com.society.controller;


import com.society.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminDashboardController {

    private final NoticeService noticeService;

    @GetMapping("/notices/count")
    public long getNoticeCount() {
        return noticeService.getNoticeCount();
    }
}
