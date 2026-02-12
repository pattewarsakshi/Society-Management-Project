package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import com.society.entity.Society;
import com.society.entity.User;
import com.society.entityenum.Role;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Used in login
    Optional<User> findByEmail(String email);

    // Used in registration 
    boolean existsByEmail(String email);
    
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

	Optional<User> findByResetToken(String token);

}
