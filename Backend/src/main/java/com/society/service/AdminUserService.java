package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.ApproveUserRequestDTO;
import com.society.dto.PendingApprovalUserDTO;
import com.society.entity.Flat;
import com.society.entity.User;
import com.society.entityenum.FlatStatus;
import com.society.entityenum.Role;
import com.society.entityenum.AccountStatus;
import com.society.repository.FlatRepository;
import com.society.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final FlatRepository flatRepository;

    // ============================================================
    // DASHBOARD COUNT (Resident + Guard)
    // ============================================================
    public Integer getPendingApprovalCount(Integer societyId) {

        Integer residentCount =
                userRepository.countByRoleAndFlatIsNullAndSociety_SocietyId(
                        Role.RESIDENT,
                        societyId
                );

        Integer guardCount =
                userRepository.countByRoleAndAccountStatusAndSociety_SocietyId(
                        Role.GUARD,
                        AccountStatus.PENDING,
                        societyId
                );

        return residentCount + guardCount;
    }

    // ============================================================
    // RESIDENT APPROVAL
    // ============================================================

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

    // ============================================================
    // GUARD APPROVAL FLOW
    // ============================================================

    public List<User> getPendingGuards(Integer societyId) {
        return userRepository
                .findByRoleAndAccountStatusAndSociety_SocietyId(
                        Role.GUARD,
                        AccountStatus.PENDING,
                        societyId
                );
    }

    // 🔥 MISSING METHOD ADDED
    public Integer getPendingGuardCount(Integer societyId) {
        return userRepository
                .countByRoleAndAccountStatusAndSociety_SocietyId(
                        Role.GUARD,
                        AccountStatus.PENDING,
                        societyId
                );
    }

    public void approveGuard(Integer guardId) {

        User guard = userRepository.findById(guardId)
                .orElseThrow(() -> new RuntimeException("Guard not found"));

        if (guard.getRole() != Role.GUARD) {
            throw new RuntimeException("User is not a guard");
        }

        guard.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(guard);
    }

    public void rejectGuard(Integer guardId) {

        User guard = userRepository.findById(guardId)
                .orElseThrow(() -> new RuntimeException("Guard not found"));

        if (guard.getRole() != Role.GUARD) {
            throw new RuntimeException("User is not a guard");
        }

        guard.setAccountStatus(AccountStatus.REJECTED);
        userRepository.save(guard);
    }
}
