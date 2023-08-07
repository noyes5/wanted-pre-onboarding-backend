package com.wanted.onboarding.board.service;

import com.wanted.onboarding.board.entity.Board;
import com.wanted.onboarding.board.repository.BoardRepository;
import com.wanted.onboarding.users.entity.Users;
import com.wanted.onboarding.users.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<Board> boardList(Pageable pageable) {
        return null;
    }

    @Override
    public Board boardView(Integer id) {
        return null;
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
