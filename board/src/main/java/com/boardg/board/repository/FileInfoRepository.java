package com.boardg.board.repository;

import com.boardg.board.model.entity.FileInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>{
    
}
