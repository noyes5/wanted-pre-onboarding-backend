package com.wanted.onboarding.board.controller;

import com.wanted.onboarding.board.util.PaginationUtil;
import com.wanted.onboarding.board.dto.BoardDTO;
import com.wanted.onboarding.board.dto.BoardDetailDTO;
import com.wanted.onboarding.board.dto.BoardListDTO;
import com.wanted.onboarding.board.entity.Board;
import com.wanted.onboarding.board.service.BoardService;
import com.wanted.onboarding.exception.NotFoundBoardException;
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
        String email = jwtTokenProvider.validateTokenAndExtractEmail(request);

        if (email == null) {
            return new ResponseEntity<>("로그인 상태가 아닙니다. 로그인 해주세요.", HttpStatus.UNAUTHORIZED);
        }
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
        page = PaginationUtil.adjustPageNumber(page, totalPage);

        int startPage = PaginationUtil.calculateStartPage(page);
        int endPage = PaginationUtil.calculateEndPage(startPage, totalPage);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page", String.valueOf(page));
        responseHeaders.add("size", String.valueOf(size));
        responseHeaders.add("total-page", String.valueOf(totalPage));
        responseHeaders.add("start-page", String.valueOf(startPage));
        responseHeaders.add("end-page", String.valueOf(endPage));
        return new ResponseEntity<>(boardPage.getContent(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDTO> viewBoard(@PathVariable Integer boardId) {
        try {
            BoardDetailDTO boardDetailDTO = boardService.viewBoardDetail(boardId);
            return new ResponseEntity<>(boardDetailDTO, HttpStatus.OK);
        } catch (NotFoundBoardException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable Integer boardId
                                                    , @RequestBody BoardDTO boardDTO, HttpServletRequest request) {
        String email = jwtTokenProvider.validateTokenAndExtractEmail(request);
        if (email == null) {
            return new ResponseEntity<>("로그인 상태가 아닙니다. 로그인 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        Board boardTemp = boardService.viewBoard(boardId);
        String authorEmail = boardTemp.getUser().getEmail();

        if (!authorEmail.equals(email)) {
            return new ResponseEntity<>("본인의 글만 수정할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        System.out.println("글쓴사람이메일" + authorEmail);
        boardTemp.setTitle(boardDTO.getTitle());
        boardTemp.setContent(boardDTO.getContent());

        boardService.writeBoard(boardTemp, email);

            return new ResponseEntity<>("글 작성자: " + email
                + ", 제목: " + boardTemp.getTitle()
                + ", 내용 : " + boardTemp.getContent()
                + ", 글 직성 시간:" + boardTemp.getCreatedAt()
                + "글수정시간: " + boardTemp.getUpdatedAt(), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Integer boardId, HttpServletRequest request) {
        String email = jwtTokenProvider.validateTokenAndExtractEmail(request);
        if (email == null) {
            return new ResponseEntity<>("로그인 상태가 아닙니다. 로그인 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        Board boardTemp = boardService.viewBoard(boardId);
        String authorEmail = boardTemp.getUser().getEmail();

        if (!authorEmail.equals(email)) {
            return new ResponseEntity<>("본인의 글만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
