package com.boardg.board.repository;

import java.util.List;

import com.boardg.board.model.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    
}
