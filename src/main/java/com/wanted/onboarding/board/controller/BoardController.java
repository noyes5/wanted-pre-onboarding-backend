package com.wanted.onboarding.board.controller;

import com.wanted.onboarding.board.dto.BoardDTO;
import com.wanted.onboarding.board.entity.Board;
import com.wanted.onboarding.board.service.BoardService;
import com.wanted.onboarding.security.JwtTokenProvider;
import com.wanted.onboarding.users.entity.Users;
import com.wanted.onboarding.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/write")
    public ResponseEntity<String> writePost(@RequestBody BoardDTO boardDTO, HttpServletRequest request) {
        String token = jwtTokenProvider.extractTokenFromRequest(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>("로그인 상태가 아닙니다. 로그인 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        String email = jwtTokenProvider.getEmailFromToken(token);

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        boardService.writeBoard(board, email);

        return new ResponseEntity<>("글 작성자: " + email
                                        + ", 제목: " + boardDTO.getTitle()
                                        + ", 내용 : " + boardDTO.getContent(), HttpStatus.OK);
    }
}
