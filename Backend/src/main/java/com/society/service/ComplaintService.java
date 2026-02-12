package com.society.service;

import java.util.List;

import com.society.dto.AdminComplaintSearchDTO;
import com.society.dto.ComplaintRequestDTO;
import com.society.dto.ComplaintResponseDTO;
import com.society.dto.ComplaintStatusUpdateDTO;
import com.society.entity.Complaint;

import jakarta.servlet.http.HttpSession;

public interface ComplaintService {

    // Resident
	ComplaintResponseDTO raiseComplaint(ComplaintRequestDTO dto, HttpSession session);

    List<ComplaintResponseDTO> getMyComplaints(HttpSession session);

    // Admin
    List<ComplaintResponseDTO> getComplaintsByFlat(
            AdminComplaintSearchDTO dto,
            HttpSession session);

    ComplaintResponseDTO updateStatus(
            Integer complaintId,
            ComplaintStatusUpdateDTO dto,
            HttpSession session);

	List<ComplaintResponseDTO> getAllComplaintsForAdmin(HttpSession session);
	
    long getPendingComplaintCount();

	long getMyPendingComplaintCount(HttpSession session);

}