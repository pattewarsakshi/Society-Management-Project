package com.society.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.society.dto.*;
import com.society.service.ComplaintService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // ================= RESIDENT =================
    @PostMapping
    public ComplaintResponseDTO raiseComplaint(
            @RequestBody ComplaintRequestDTO dto,
            HttpSession session) {

        return complaintService.raiseComplaint(dto, session);
    }

    @GetMapping("/my")
    public List<ComplaintResponseDTO> myComplaints(HttpSession session) {

        return complaintService.getMyComplaints(session);
    }

    // ================= ADMIN =================
    @PostMapping("/admin/search")
    public List<ComplaintResponseDTO> search(
            @RequestBody AdminComplaintSearchDTO dto,
            HttpSession session) {

        return complaintService.getComplaintsByFlat(dto, session);
    }

    @PutMapping("/{id}/status")
    public ComplaintResponseDTO updateStatus(
            @PathVariable Integer id,
            @RequestBody ComplaintStatusUpdateDTO dto,
            HttpSession session) {

        return complaintService.updateStatus(id, dto, session);
    }
    
    // ================= ADMIN =================
    @GetMapping("/admin/all")
    public List<ComplaintResponseDTO> getAllComplaints(HttpSession session) {
        return complaintService.getAllComplaintsForAdmin(session);
    }
    
    //  Dashboard bucket
    @GetMapping("/pending/count")
    public ComplaintCountDTO getPendingComplaintCount() {
        return new ComplaintCountDTO(
                complaintService.getPendingComplaintCount()
        );
    }
    
    //  RESIDENT: Pending complaints count (session-based)
    @GetMapping("/my/pending/count")
    public ComplaintCountDTO getMyPendingComplaintCount(HttpSession session) {
        return new ComplaintCountDTO(
            complaintService.getMyPendingComplaintCount(session)
        );
    }
}
