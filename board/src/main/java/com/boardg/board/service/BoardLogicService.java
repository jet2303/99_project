package com.boardg.board.service;

import java.time.LocalDateTime;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.enumclass.BoardStatus;
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
    public BoardApiResponse create(BoardApiRequest request) {
        

        Board board = Board.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .status(BoardStatus.REGISTERED)
                            .registeredAt(LocalDateTime.now())
                            .build();
        Board newBoard = baseRepository.save(board);

        return response(newBoard);
    }

    @Override
    public BoardApiResponse read(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        log.info("read response() : {}", response(board));
        return response(board);
    }

    @Override
    public BoardApiResponse update(BoardApiRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BoardApiResponse delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow( () -> new RuntimeException("삭제할 게시글이 없습니다."));
        board.setStatus(BoardStatus.UNREGISTERED);
        return BoardApiResponse.builder()
                                .id(id)
                                .title(board.getTitle())
                                .content(board.getContent())
                                .status(board.getStatus())
                                .registeredAt(board.getRegisteredAt())
                                .unregisteredAt(board.getUnregisteredAt())
                                .build();        
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
