package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponseDTO {

    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Integer societyId;
    private Integer flatId;
}
