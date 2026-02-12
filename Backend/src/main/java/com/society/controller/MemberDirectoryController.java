package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.MemberDirectoryDTO;
import com.society.entity.User;
import com.society.service.MemberDirectoryService;
import com.society.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberDirectoryController {

    private final MemberDirectoryService memberDirectoryService;
    private final UserService userService;

    @GetMapping("/directory")
    public ResponseEntity<List<MemberDirectoryDTO>> getMembersDirectory(HttpSession session) {

        User loggedUser = userService.getLoggedInUser(session);

        List<MemberDirectoryDTO> members =
                memberDirectoryService.getMembersDirectory(
                        loggedUser.getSociety().getSocietyId(),
                        loggedUser.getRole()
                );

        return ResponseEntity.ok(members);
    }
}
