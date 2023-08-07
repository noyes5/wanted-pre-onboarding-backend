package com.wanted.onboarding.board.service;

import com.wanted.onboarding.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    void writeBoard(Board board, String email);
    Page<Board> boardList(Pageable pageable);
    Board boardView(Integer id);
    Page<Board> boardSearchList(String searchKeyword, Pageable pageable);
    void boardDelete(Integer id);

    void boardUpdate(Board board);
}
