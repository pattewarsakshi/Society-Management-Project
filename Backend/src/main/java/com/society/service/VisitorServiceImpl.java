package com.society.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;
import com.society.entity.Visitor;
import com.society.entityenum.VehicleType;
import com.society.entityenum.VisitPurpose;
import com.society.repository.VisitorRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    @Override
    public VisitorResponseDTO addVisitor(
            VisitorEntryDTO dto,
            MultipartFile photo,
            HttpSession session
    ) {

        Integer societyId = (Integer) session.getAttribute("societyId");

        if (dto.getPhone() != null &&
                visitorRepository
                        .findByPhoneAndExitTimeIsNullAndSociety_SocietyId(
                                dto.getPhone(), societyId)
                        .isPresent()) {
            throw new RuntimeException("Visitor is already inside");
        }

        Visitor visitor = new Visitor();
        visitor.setVisitorName(dto.getVisitorName());
        visitor.setPhone(dto.getPhone());
        visitor.setPurpose(VisitPurpose.valueOf(dto.getPurpose()));
        visitor.setEntryTime(LocalDateTime.now());
        visitor.setFlatId(dto.getFlatId());

        if (dto.getVehicleType() != null) {
            visitor.setVehicleType(VehicleType.valueOf(dto.getVehicleType()));
        }

        visitor.setVehicleNumber(dto.getVehicleNumber());

        if (photo != null && !photo.isEmpty()) {
            visitor.setVisitorPhoto(photo.getOriginalFilename());
        }

        Visitor saved = visitorRepository.save(visitor);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getAllVisitors(HttpSession session) {

        Integer societyId = (Integer) session.getAttribute("societyId");

        return visitorRepository
                .findBySociety_SocietyId(societyId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getInsideVisitors(HttpSession session) {

        Integer societyId = (Integer) session.getAttribute("societyId");

        return visitorRepository
                .findBySociety_SocietyIdAndExitTimeIsNull(societyId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getTodayVisitors(HttpSession session) {

        Integer societyId = (Integer) session.getAttribute("societyId");

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        return visitorRepository
                .findBySociety_SocietyIdAndEntryTimeBetween(
                        societyId, start, end)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VisitorResponseDTO exitVisitor(
            Integer visitorId,
            HttpSession session
    ) {

        Integer societyId = (Integer) session.getAttribute("societyId");

        Visitor visitor = visitorRepository
                .findByVisitorIdAndSociety_SocietyId(visitorId, societyId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        if (visitor.getExitTime() != null) {
            throw new RuntimeException("Visitor already exited");
        }

        visitor.setExitTime(LocalDateTime.now());

        Visitor updated = visitorRepository.save(visitor);
        return mapToDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getAllVisitorsForAdmin(HttpSession session) {
        return getAllVisitors(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getInsideVisitorsForAdmin(HttpSession session) {
        return getInsideVisitors(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getTodayVisitorsForAdmin(HttpSession session) {
        return getTodayVisitors(session);
    }

    private VisitorResponseDTO mapToDTO(Visitor v) {

        VisitorResponseDTO dto = new VisitorResponseDTO();
        dto.setVisitorId(v.getVisitorId());
        dto.setVisitorName(v.getVisitorName());
        dto.setPhone(v.getPhone());
        dto.setPurpose(v.getPurpose().name());
        dto.setEntryTime(v.getEntryTime());
        dto.setExitTime(v.getExitTime());
        dto.setVehicleType(
                v.getVehicleType() != null ? v.getVehicleType().name() : null);
        dto.setVehicleNumber(v.getVehicleNumber());
        dto.setVisitorPhoto(v.getVisitorPhoto());
        dto.setFlatId(v.getFlatId());

        return dto;
    }
}
