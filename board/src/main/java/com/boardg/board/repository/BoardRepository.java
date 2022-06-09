package com.boardg.board.repository;

import java.util.List;
import java.util.Optional;

import com.boardg.board.model.dto.BoardAndFileInfoProjectionInterafce;
import com.boardg.board.model.dto.BoardDto;
import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;
import com.boardg.board.model.enumclass.BoardStatus;
import com.boardg.board.model.network.response.BoardApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    
    Optional<List<Board>> findByStatus(BoardStatus boardStatus);

    Optional<Page<Board>> findByTitleAndStatus(String title, BoardStatus boardStatus, Pageable pageable);

    Optional<Page<Board>> findByStatus(BoardStatus boardStatus, Pageable pageable);

    //table 이름이 아닌 객체의 이름으로 짤것.
    // @Query(value = "select f from FileInfo f left join Board b f.board where f.boardIdx=:idx")
    // @Query(value = "select fileinfo, board from FileInfo fileinfo Left join fileinfo.board board where fileinfo.boardIdx=:idx")
    @Query(value = "select b, f from Board b left join b.fileInfoList f where b.id=:idx")
    Optional<List<Board>> findDetail(@Param("idx") Long id);
    
}
