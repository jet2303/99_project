package com.boardg.board.repository;

import java.util.List;
import java.util.Optional;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.enumclass.BoardStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    
    Optional<List<Board>> findByStatus(BoardStatus boardStatus);
}
