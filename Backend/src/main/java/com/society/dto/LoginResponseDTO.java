package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private Integer userId;
    private String name;
    private String role;
    private Integer societyId;
    private Integer flatId;
    private String email;
    private String phone;

    // ✅ newly added
    private String societyName;
}
