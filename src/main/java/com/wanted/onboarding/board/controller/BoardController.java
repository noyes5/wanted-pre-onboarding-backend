package com.wanted.onboarding.board.controller;

import com.wanted.onboarding.board.PaginationUtils;
import com.wanted.onboarding.board.dto.BoardDTO;
import com.wanted.onboarding.board.dto.BoardListDTO;
import com.wanted.onboarding.board.entity.Board;
import com.wanted.onboarding.board.service.BoardService;
import com.wanted.onboarding.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BoardListDTO>> getBoardPage(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Page<BoardListDTO> boardPage = boardService.getBoardList(page, size);
        int totalPage = boardPage.getTotalPages();
        page = PaginationUtils.adjustPageNumber(page, totalPage);

        int startPage = PaginationUtils.calculateStartPage(page);
        int endPage = PaginationUtils.calculateEndPage(startPage, totalPage);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page", String.valueOf(page));
        responseHeaders.add("size", String.valueOf(size));
        responseHeaders.add("total-page", String.valueOf(totalPage));
        responseHeaders.add("start-page", String.valueOf(startPage));
        responseHeaders.add("end-page", String.valueOf(endPage));
        return new ResponseEntity<>(boardPage.getContent(), responseHeaders, HttpStatus.OK);
    }
}
