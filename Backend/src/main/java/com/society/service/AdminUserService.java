package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.ApproveUserRequestDTO;
import com.society.dto.PendingApprovalUserDTO;
import com.society.entity.Flat;
import com.society.entity.User;
import com.society.entityenum.FlatStatus;
import com.society.entityenum.Role;
import com.society.repository.FlatRepository;
import com.society.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final FlatRepository flatRepository;

    // dashboard count
    public Integer getPendingApprovalCount(Integer societyId) {
        return userRepository.countByRoleAndFlatIsNullAndSociety_SocietyId(
                Role.RESIDENT,
                societyId
        );
    }

    //  list for approve users page
    public List<PendingApprovalUserDTO> getPendingUsers(Integer societyId) {

        return userRepository
                .findByRoleAndFlatIsNullAndSociety_SocietyId(
                        Role.RESIDENT,
                        societyId
                )
                .stream()
                .map(user -> new PendingApprovalUserDTO(
                        user.getUserId(),
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLastName(),
                        user.getPhone(),
                        user.getRegistrationDate(),
                        user.getSociety().getSocietyName()
                ))
                .toList();
    }

    //  approve user
    public void approveUser(ApproveUserRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flat flat = flatRepository.findById(dto.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        user.setFlat(flat);
        userRepository.save(user);

        flat.setStatus(FlatStatus.OCCUPIED);
        flatRepository.save(flat);
    }
}

