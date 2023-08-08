package com.wanted.onboarding.board.service;

import com.wanted.onboarding.board.dto.BoardDTO;
import com.wanted.onboarding.board.dto.BoardDetailDTO;
import com.wanted.onboarding.board.dto.BoardListDTO;
import com.wanted.onboarding.board.entity.Board;
import com.wanted.onboarding.board.repository.BoardRepository;
import com.wanted.onboarding.users.entity.Users;
import com.wanted.onboarding.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void writeBoard(Board board, String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저의 이메일이 존재하지 않습니다."));
        board.setUser(user);
        boardRepository.save(board);
    }

    @Override
    public Page<BoardListDTO> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("boardId").descending());
        return boardRepository.findAll(pageable).map(board -> BoardListDTO.builder()
                .boardId(board.getBoardId())
                .userEmail(board.getUser().getEmail())
                .title(board.getTitle())
                .createdAt(board.getCreatedAt())
                .build());
    }

    @Override
    public BoardDetailDTO viewBoard(Integer boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            return BoardDetailDTO.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .userEmail(board.getUser().getEmail())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .build();
        } else {
            return null;
        }

    }

    @Override
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        return null;
    }

    @Override
    public void boardDelete(Integer id) {

    }

    @Override
    public void boardUpdate(Board board) {

    }
}
