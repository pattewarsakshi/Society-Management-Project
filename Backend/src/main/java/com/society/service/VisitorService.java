package com.society.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;

import jakarta.servlet.http.HttpSession;

public interface VisitorService {

    //  Add visitor
    VisitorResponseDTO addVisitor(
            VisitorEntryDTO dto,
            MultipartFile photo,
            HttpSession session
    );

    //  Visitors currently inside
    List<VisitorResponseDTO> getInsideVisitors(HttpSession session);

    // Today visitors
    List<VisitorResponseDTO> getTodayVisitors(HttpSession session);

    // All visitors
    List<VisitorResponseDTO> getAllVisitors(HttpSession session);

    //  Exit visitor
    VisitorResponseDTO exitVisitor(
            Integer visitorId,
            HttpSession session
    );
    
    List<VisitorResponseDTO> getAllVisitorsForAdmin(HttpSession session);
    List<VisitorResponseDTO> getInsideVisitorsForAdmin(HttpSession session);
    List<VisitorResponseDTO> getTodayVisitorsForAdmin(HttpSession session);
}
