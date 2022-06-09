package com.boardg.board.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>{
    
    Optional<List<FileInfo>> findByBoard(Board board);

    //연관관계인 board를 key로 삭제....
    //리펙토링시 file_info 테이블의 board_id로 할수있을지 확인.
    @Transactional
    @Modifying
    @Query(value = "delete from FileInfo where board=:board")
    void deletefile(@Param("board") Board board);
}
