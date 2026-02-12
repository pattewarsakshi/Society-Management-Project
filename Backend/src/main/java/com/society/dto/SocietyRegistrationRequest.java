package com.society.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyRegistrationRequest {

    @NotBlank
    private String societyName;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String pincode;

    @Email
    @NotBlank
    private String adminEmail;

    @NotBlank
    private String adminPassword;
}
