package com.boardg.board.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardStatus {
    REGISTERED(0,"등록","게시글 등록됨")
    ,UNREGISTERED(1,"삭제","게시글 삭제됨")
    ;

    Integer id;
    String status;
    String description;
}
