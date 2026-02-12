package com.society.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;
import com.society.entity.Visitor;
import com.society.entityenum.VehicleType;
import com.society.entityenum.VisitPurpose;
import com.society.repository.VisitorRepository;
import com.society.service.VisitorService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    //  Add visitor
    @Override
    public VisitorResponseDTO addVisitor(
            VisitorEntryDTO dto,
            MultipartFile photo,
            HttpSession session
    ) {

        Visitor visitor = new Visitor();
        visitor.setVisitorName(dto.getVisitorName());
        visitor.setPhone(dto.getPhone());
        visitor.setPurpose(VisitPurpose.valueOf(dto.getPurpose()));
        visitor.setEntryTime(LocalDateTime.now());

        visitor.setVehicleType(
                dto.getVehicleType() != null
                        ? VehicleType.valueOf(dto.getVehicleType())
                        : null
        );
        visitor.setVehicleNumber(dto.getVehicleNumber());

        // ONLY ADDITION
        visitor.setFlatId(dto.getFlatId());

        if (photo != null && !photo.isEmpty()) {
            visitor.setVisitorPhoto(photo.getOriginalFilename());
        }

        Visitor saved = visitorRepository.save(visitor);
        return mapToDTO(saved);
    }

    //Visitors currently inside
    @Override
    public List<VisitorResponseDTO> getInsideVisitors(HttpSession session) {
        return visitorRepository.findAll().stream()
                .filter(v -> v.getExitTime() == null)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Today visitors
    @Override
    public List<VisitorResponseDTO> getTodayVisitors(HttpSession session) {
        LocalDate today = LocalDate.now();

        return visitorRepository.findAll().stream()
                .filter(v -> v.getEntryTime() != null &&
                        v.getEntryTime().toLocalDate().equals(today))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // All visitors
    @Override
    public List<VisitorResponseDTO> getAllVisitors(HttpSession session) {
        return visitorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Exit visitor
    @Override
    public VisitorResponseDTO exitVisitor(
            Integer visitorId,
            HttpSession session
    ) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        visitor.setExitTime(LocalDateTime.now());

        Visitor updated = visitorRepository.save(visitor);
        return mapToDTO(updated);
    }

    // Admin – all visitors
    @Override
    public List<VisitorResponseDTO> getAllVisitorsForAdmin(HttpSession session) {
        return getAllVisitors(session);
    }

    // Admin – inside visitors
    @Override
    public List<VisitorResponseDTO> getInsideVisitorsForAdmin(HttpSession session) {
        return getInsideVisitors(session);
    }

    //  Admin – today visitors
    @Override
    public List<VisitorResponseDTO> getTodayVisitorsForAdmin(HttpSession session) {
        return getTodayVisitors(session);
    }

    //  Mapper
    private VisitorResponseDTO mapToDTO(Visitor v) {

        VisitorResponseDTO dto = new VisitorResponseDTO();
        dto.setVisitorId(v.getVisitorId());
        dto.setVisitorName(v.getVisitorName());
        dto.setPhone(v.getPhone());
        dto.setPurpose(v.getPurpose().name());
        dto.setEntryTime(v.getEntryTime());
        dto.setExitTime(v.getExitTime());
        dto.setVehicleType(v.getVehicleType() != null ? v.getVehicleType().name() : null);
        dto.setVehicleNumber(v.getVehicleNumber());
        dto.setVisitorPhoto(v.getVisitorPhoto());

        
        dto.setFlatId(v.getFlatId());

        return dto;
    }
}
