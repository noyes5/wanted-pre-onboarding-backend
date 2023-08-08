package com.wanted.onboarding.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDetailDTO{
    private int boardId;
    private String title;
    private String content;
    private String userEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}