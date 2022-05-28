package com.boardg.board.model.dto;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;

public interface BoardAndFileInfoProjectionInterafce {
    Board getBoard();

    FileInfo getFileInfo();
}
