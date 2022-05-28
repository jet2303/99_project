package com.boardg.board.model.dto;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private Board board;

    private FileInfo fileInfo;
    
}
