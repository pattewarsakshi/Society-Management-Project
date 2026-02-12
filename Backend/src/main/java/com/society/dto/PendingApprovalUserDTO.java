package com.society.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingApprovalUserDTO {

    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private LocalDate registrationDate;
    private String societyName;
}
