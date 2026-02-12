package com.society.service;

import com.society.dto.NoticeRequestDto;
import com.society.dto.NoticeResponseDto;
import com.society.entity.Notice;
import com.society.entity.User;
import com.society.repository.NoticeRepository;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository; // ✅ ADD THIS

    // ================= GET LOGGED IN USER =================
    private User getLoggedInUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

        if (userId == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= CREATE NOTICE =================
    @Transactional
    public NoticeResponseDto createNotice(
            NoticeRequestDto dto,
            HttpSession session) {

        User user = getLoggedInUser(session);

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setDescription(dto.getDescription());
        notice.setPostedBy(user);
        notice.setEmailSent(false);

        Notice saved = noticeRepository.save(notice);
        return map(saved);
    }

    // ================= GET NOTICES =================
    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getNotices(HttpSession session) {

        User user = getLoggedInUser(session);
        Integer societyId = user.getSociety().getSocietyId();

        return noticeRepository
                .findByPostedBy_Society_SocietyIdOrderByCreatedAtDesc(societyId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ================= UPDATE NOTICE =================
    @Transactional
    public NoticeResponseDto updateNotice(
            Integer id,
            NoticeRequestDto dto,
            HttpSession session) {

        User user = getLoggedInUser(session);

        if (!user.getRole().name().equals("ADMIN")) {
            throw new RuntimeException("Only admin can update notices");
        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        notice.setTitle(dto.getTitle());
        notice.setDescription(dto.getDescription());

        return map(noticeRepository.save(notice));
    }

    // ================= DELETE NOTICE =================
    @Transactional
    public void deleteNotice(Integer id, HttpSession session) {

        User user = getLoggedInUser(session);

        if (!user.getRole().name().equals("ADMIN")) {
            throw new RuntimeException("Only admin can delete notices");
        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        noticeRepository.delete(notice);
    }

    public long getNoticeCount() {
        return noticeRepository.count();
    }
    
    // ================= DTO MAPPER =================
    private NoticeResponseDto map(Notice notice) {
        return new NoticeResponseDto(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getDescription(),
                notice.getPostedBy().getFirstName(),
                notice.getCreatedAt()
        );
    }
}
