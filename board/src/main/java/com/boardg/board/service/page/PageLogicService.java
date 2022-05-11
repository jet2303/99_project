package com.boardg.board.service.page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.enumclass.BoardStatus;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;
import com.boardg.board.service.BoardLogicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class PageLogicService {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardLogicService boardLogicService;

    //게시글 리스트
    public List<Board> getBoardList(){
        // List<Board> boardList = boardRepository.findAll();
        List<Board> boardList = boardRepository.findByStatus(BoardStatus.REGISTERED).orElseThrow( () -> new RuntimeException("게시글이 없습니다."));

        if(boardList.size()<1){
            // return new RuntimeException();
        }
        List<BoardApiResponse> boardApiResponses = new ArrayList<>();

        boardList.stream().map( board-> boardApiResponses.add(response(board)));

        return boardList;
    }

    //게시글 상세뷰
    public Board getBoardDetail(Long id){
        // Board board = new Board();
        // if(id==null){
        //     return board;
        // }
        // else{
        //     board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        // }
        // return board;

        // BoardApiResponse boardApiResponse =  boardLogicService.read(id);
        // return Board.builder()
        //             .id(boardApiResponse.getId())
        //             .title(boardApiResponse.getTitle())
        //             .content(boardApiResponse.getContent())
        //             .registeredAt(boardApiResponse.getRegisteredAt())
        //             .status(boardApiResponse.getStatus())
        //             .unregisteredAt(boardApiResponse.getUnregisteredAt())
        //             .build();
        Board board = boardRepository.findById(id).orElseThrow( () -> new RuntimeException("not found board"));
        return board;
        
    } 

    public Board createBoard(Board newBoard){
        
        newBoard.setRegisteredAt(LocalDateTime.now())
                .setStatus(BoardStatus.REGISTERED);
        Board board = boardRepository.save(newBoard);

        return board;
    }

    //게시글 업데이트
    public Board updateBoard(Board newBoard){
        Board board =  boardRepository.findById(newBoard.getId()).orElseThrow( () -> new RuntimeException("해당 게시글이 없습니다."));
        board.setTitle(newBoard.getTitle())
            .setContent(newBoard.getContent())
            .setRegisteredAt(newBoard.getRegisteredAt())
            .setStatus(BoardStatus.REGISTERED)
            .setUnregisteredAt(newBoard.getUnregisteredAt());
        //save때 error 체크
        boardRepository.save(board);
        
        return board;
    }

    //게시글 삭제
    public Board delBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("삭제할 게시글이 없습니다."));
        board.setStatus(BoardStatus.UNREGISTERED);
        boardRepository.save(board);
        return board;
    }

    


    private BoardApiRequest request(Board board){
        BoardApiRequest boardApiRequest = BoardApiRequest.builder()
                                            .id(board.getId())
                                            .title(board.getTitle())
                                            .content(board.getContent())
                                            .registeredAt(board.getRegisteredAt())
                                            .unregisteredAt(board.getUnregisteredAt())
                                            .status(board.getStatus())
                                            .build();
        return boardApiRequest;
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
