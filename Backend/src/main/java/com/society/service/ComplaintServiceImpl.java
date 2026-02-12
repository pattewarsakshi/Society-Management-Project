package com.society.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.*;
import com.society.entity.Complaint;
import com.society.entity.User;
import com.society.entityenum.ComplaintStatus;
import com.society.entityenum.Role;
import com.society.repository.ComplaintRepository;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepo;
    private final UserRepository userRepository;


    // ================= RESIDENT =================
    public long getMyPendingComplaintCount(HttpSession session) {
        User user = getLoggedInUser(session); // same helper you already use
        return complaintRepo.countByUserAndStatus(
            user,
            ComplaintStatus.OPEN
        );
    }
    
    
    @Override
    public ComplaintResponseDTO raiseComplaint(
            ComplaintRequestDTO dto,
            HttpSession session) {

        User user = getLoggedInUser(session);

        if (user.getRole() != Role.RESIDENT) {
            throw new RuntimeException("Only residents can raise complaints");
        }

        if (user.getFlat() == null) {
            throw new RuntimeException("Flat not assigned");
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setFlat(user.getFlat());
        complaint.setComplaintType(dto.getComplaintType());
        complaint.setDescription(dto.getDescription());
        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setDateFiled(LocalDate.now());

        return mapToDto(complaintRepo.save(complaint));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDTO> getMyComplaints(HttpSession session) {

        User user = getLoggedInUser(session);

        return complaintRepo.findByUserUserId(user.getUserId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ================= ADMIN =================
    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDTO> getComplaintsByFlat(
            AdminComplaintSearchDTO dto,
            HttpSession session) {

        User admin = getLoggedInUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }

        return complaintRepo
                .findByFlatSocietySocietyIdAndFlatTowerNameAndFlatFlatNumber(
                        admin.getSociety().getSocietyId(),
                        dto.getTowerName(),
                        dto.getFlatNumber()
                )
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public ComplaintResponseDTO updateStatus(
            Integer complaintId,
            ComplaintStatusUpdateDTO dto,
            HttpSession session) {

        User admin = getLoggedInUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can update status");
        }

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(dto.getStatus());

        if (dto.getStatus() == ComplaintStatus.RESOLVED
                || dto.getStatus() == ComplaintStatus.CLOSED) {
            complaint.setDateResolved(LocalDate.now());
        }

        return mapToDto(complaintRepo.save(complaint));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDTO> getAllComplaintsForAdmin(HttpSession session) {

        User admin = getLoggedInUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }

        // Get all complaints in the admin's society
        return complaintRepo.findByFlatSocietySocietyId(admin.getSociety().getSocietyId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    
    // ================= MAPPER =================
    private ComplaintResponseDTO mapToDto(Complaint c) {

        ComplaintResponseDTO dto = new ComplaintResponseDTO();
        dto.setComplaintId(c.getComplaintId());
        dto.setComplaintType(c.getComplaintType());
        dto.setDescription(c.getDescription());
        dto.setStatus(c.getStatus().name());
        dto.setDateFiled(c.getDateFiled());
        dto.setDateResolved(c.getDateResolved());
        dto.setFlatId(c.getFlat().getFlatId());
        dto.setUserId(c.getUser().getUserId());

        return dto;
    }

    // ================= HELPER =================
    private User getLoggedInUser(HttpSession session) {

        Object obj = session.getAttribute("LOGGED_USER_ID");

        if (obj == null) {
            throw new RuntimeException("User not logged in");
        }
        Integer userId = (Integer) obj;

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    public long getPendingComplaintCount() {
        return complaintRepo.countByStatusIn(
                Set.of(
                        ComplaintStatus.OPEN,
                        ComplaintStatus.IN_PROGRESS
                )
        );
    }
}
