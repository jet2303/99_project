package com.boardg.board.service;

import java.time.LocalDateTime;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.enumclass.BoardStatus;
import com.boardg.board.model.network.Header;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardLogicService extends BaseService<BoardApiRequest, BoardApiResponse, Board>{

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Header<BoardApiResponse> create(Header<BoardApiRequest> request) {
        BoardApiRequest boardApiRequest = request.getData();

        Board board = Board.builder()
                            .title(boardApiRequest.getTitle())
                            .content(boardApiRequest.getContent())
                            .status(BoardStatus.REGISTERED)
                            .registeredAt(LocalDateTime.now())
                            .build();
        Board newBoard = baseRepository.save(board);

        return Header.OK(response(newBoard));
    }

    @Override
    public Header<BoardApiResponse> read(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("boardlogicservice read Error"));
        log.info("Header read response() : {}", Header.OK(response(board)));
        return Header.OK(response(board));
    }

    @Override
    public Header<BoardApiResponse> update(Header<BoardApiRequest> request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Header<BoardApiResponse> delete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    private BoardApiResponse response(Board board){
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                                                            .id(board.getId())
                                                            .title(board.getTitle())
                                                            .content(board.getContent())
                                                            .registeredAt(board.getRegisteredAt())
                                                            .unregisteredAt(board.getUnregisteredAt())
                                                            .status(board.getStatus())
                                                            .build();
        return boardApiResponse;                                                                
    }

}
