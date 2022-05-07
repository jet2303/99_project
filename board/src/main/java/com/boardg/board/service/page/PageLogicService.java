package com.boardg.board.service.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.network.Header;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class PageLogicService {
    
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getBoardList(){
        List<Board> boardList = boardRepository.findAll();

        if(boardList.size()<1){
            // return new RuntimeException();
        }
        List<BoardApiResponse> boardApiResponses = new ArrayList<>();

        boardList.stream().map( board-> boardApiResponses.add(response(board)));

        return boardList;
    }

    public Board getBoardDetail(Long id){
        Board board = new Board();
        if(id==null){
            return board;
        }
        else{
            board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        }
        return board;
    } 

    public Header<BoardApiRequest> addHeader(Board board){
        return Header.OK(request(board));
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
