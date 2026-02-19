package com.society.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;

import jakarta.servlet.http.HttpSession;

public interface VisitorService {

    // Add visitor
    VisitorResponseDTO addVisitor(
            VisitorEntryDTO dto,
            MultipartFile photo,
            HttpSession session
    );

    // Get all visitors (society specific)
    List<VisitorResponseDTO> getAllVisitors(HttpSession session);

    // Get visitors currently inside (society specific)
    List<VisitorResponseDTO> getInsideVisitors(HttpSession session);

    // Get today visitors (society specific)
    List<VisitorResponseDTO> getTodayVisitors(HttpSession session);

    // Exit visitor (secure society-based lookup)
    VisitorResponseDTO exitVisitor(
            Integer visitorId,
            HttpSession session
    );

    // Admin methods
    List<VisitorResponseDTO> getAllVisitorsForAdmin(HttpSession session);

    List<VisitorResponseDTO> getInsideVisitorsForAdmin(HttpSession session);

    List<VisitorResponseDTO> getTodayVisitorsForAdmin(HttpSession session);
}
