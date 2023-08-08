package com.wanted.onboarding.board.service;

import com.wanted.onboarding.board.dto.BoardDTO;
import com.wanted.onboarding.board.dto.BoardDetailDTO;
import com.wanted.onboarding.board.dto.BoardListDTO;
import com.wanted.onboarding.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    void writeBoard(Board board, String email);
    Page<BoardListDTO> getBoardList(int page, int size);
    BoardDetailDTO viewBoardDetail(Integer boardId);
    Board viewBoard(Integer boardId);
    void deleteBoard(Integer boardId);
}
