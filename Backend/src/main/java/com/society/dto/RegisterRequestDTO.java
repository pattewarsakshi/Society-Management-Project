package com.society.dto;

import com.society.entityenum.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    private Role role;        
    private Integer societyId;
    private Integer flatId;
}
