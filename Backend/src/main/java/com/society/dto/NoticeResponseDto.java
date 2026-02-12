package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoticeResponseDto {

    private Integer noticeId;
    private String title;
    private String description;
    private String postedBy;
    private LocalDateTime createdAt;
}
