package com.society.dto;

import com.society.entityenum.ComplaintStatus;
import lombok.Data;

@Data
public class ComplaintStatusUpdateDTO {
    private ComplaintStatus status;
}
