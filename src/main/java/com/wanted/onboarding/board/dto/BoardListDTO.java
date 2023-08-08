package com.wanted.onboarding.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardListDTO {
    private int boardId;
    private String title;
    private String userEmail;
    private LocalDateTime createdAt;
}