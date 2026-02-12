package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlatDashboardCountDTO {

    private long totalFlats;
    private long occupiedFlats;
    private long vacantFlats;
}

