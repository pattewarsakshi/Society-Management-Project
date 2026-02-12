package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDirectoryDTO {

    private Integer userId;
    private String name;
    private String towerName;
    private String flatNumber;
    private String email; // nullable for RESIDENT
}
