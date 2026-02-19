package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.entityenum.AccountStatus;

public interface UserRepository extends JpaRepository<User, Integer> {

    // ================= LOGIN =================
    Optional<User> findByEmail(String email);

    // ================= REGISTRATION =================
    boolean existsByEmail(String email);

    // ================= RESIDENTS =================
    @Query("""
        SELECT u
        FROM User u
        JOIN u.flat f
        WHERE u.society.societyId = :societyId
          AND u.role = com.society.entityenum.Role.RESIDENT
    """)
    List<User> findResidentsBySocietyId(Integer societyId);

    List<User> findByRoleAndFlatIsNullAndSociety_SocietyId(
            Role role,
            Integer societyId
    );

    Integer countByRoleAndFlatIsNullAndSociety_SocietyId(
            Role role,
            Integer societyId
    );

    // ================= GUARD APPROVAL =================
    List<User> findByRoleAndAccountStatusAndSociety_SocietyId(
            Role role,
            AccountStatus accountStatus,
            Integer societyId
    );

    Integer countByRoleAndAccountStatusAndSociety_SocietyId(
            Role role,
            AccountStatus accountStatus,
            Integer societyId
    );

    // ================= PASSWORD RESET =================
    Optional<User> findByResetToken(String token);
}
