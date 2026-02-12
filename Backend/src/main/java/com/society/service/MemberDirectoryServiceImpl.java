package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.MemberDirectoryDTO;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDirectoryServiceImpl implements MemberDirectoryService {

    private final UserRepository userRepository;

	@Override
	   public List<MemberDirectoryDTO> getMembersDirectory(Integer societyId, Role role) {

        List<User> users = userRepository.findResidentsBySocietyId(societyId);

        return users.stream().map(user -> {

            String email = null;
            if (role == Role.ADMIN) {
                email = user.getEmail();
            }

            return new MemberDirectoryDTO(
                    user.getUserId(),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getFlat().getTowerName(),
                    user.getFlat().getFlatNumber(),
                    email
            );

        }).toList();
	}

}
