package com.society.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GuardRegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Integer societyId; // or derive from admin session
}

