package com.boardg.board.repository;

import java.util.List;
import java.util.Optional;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>{
    
    Optional<List<FileInfo>> findByBoard(Board board);
}
