package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlatDetailsDTO {

    private Integer flatId;
    private String societyName;
    private String towerName;
    private String flatNumber;
    private Double areaSqft;
    private String status;
}

