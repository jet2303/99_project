package com.boardg.board.service.page;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import com.boardg.board.model.dto.BoardAndFileInfoProjectionInterafce;
import com.boardg.board.model.dto.BoardDto;
import com.boardg.board.model.entity.Board;
import com.boardg.board.model.entity.FileInfo;
import com.boardg.board.model.enumclass.BoardStatus;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;
import com.boardg.board.repository.FileInfoRepository;
import com.boardg.board.service.BoardLogicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class PageLogicService {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @PersistenceUnit
    EntityManagerFactory emf;


    //게시글 리스트
    public List<Board> getBoardList(){
        // List<Board> boardList = boardRepository.findAll();
        List<Board> boardList = boardRepository.findByStatus(BoardStatus.REGISTERED).orElseThrow( () -> new RuntimeException("게시글이 없습니다."));

        if(boardList.size()<1){
            // return new RuntimeException();
        }
        List<BoardApiResponse> boardApiResponses = new ArrayList<>();

        boardList.stream().map( board-> boardApiResponses.add(response(board, null)));

        return boardList;
    }

    public List<Board> getBoardKeywordList(String title){
        List<Board> boardList = boardRepository.findByTitleAndStatus(title, BoardStatus.REGISTERED).orElseThrow( () -> new RuntimeException("게시글이 없습니다."));

        return boardList;
    }


    //게시글 상세뷰
    public BoardApiResponse getBoardDetail(Long id){
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
        
        // Board board = boardRepository.findById(id).orElseThrow( () -> new RuntimeException("not found board"));
        
        List<Board> result = boardRepository.findDetail(id).orElseThrow(() -> new RuntimeException("게시글 존재하지 않음."));
        
        List<FileInfo> fileList = result.get(0).getFileInfoList();

        log.info("service response : {}", response(result.get(0), fileList));
        return response(result.get(0), fileList);
    } 

    //Exception custom Error 화면으로 바꿀것.
    public BoardApiResponse createBoard(Board newBoard, List<MultipartFile> files) throws IOException{

        String filePath = "D:\\fastcampus\\99_project\\board\\src\\main\\resources\\static\\files";
        
        newBoard.setRegisteredAt(LocalDateTime.now())
                .setStatus(BoardStatus.REGISTERED);
        Board board = boardRepository.save(newBoard);

        List<FileInfo> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(filePath,fileName);
            file.transferTo(saveFile);
            FileInfo fileInfo = new FileInfo(fileName, "/files/"+ fileName);
            fileInfo.setBoard(board);
            fileList.add(fileInfo);
        }

        fileInfoRepository.saveAll(fileList);

        return response(board, fileList);
    }

    //게시글 업데이트
    public Board updateBoard(Board newBoard, List<MultipartFile> files){
        Board board =  boardRepository.findById(newBoard.getId()).orElseThrow( () -> new RuntimeException("해당 게시글이 없습니다."));
        board.setTitle(newBoard.getTitle())
            .setContent(newBoard.getContent())
            .setRegisteredAt(newBoard.getRegisteredAt())
            .setStatus(BoardStatus.REGISTERED)
            .setUnregisteredAt(newBoard.getUnregisteredAt());
        
        boardRepository.save(board);

        List<String> updateFileList = files.stream().map(file->file.getOriginalFilename()).collect(Collectors.toList());
        //수정된 첨부파일 유무에 따른 조건 추가
        //DB에서 게시글에 해당하는 첨부파일 목록 확인 및 수정된 첨부파일 목록 비교.
        //1. 첨부파일이 수정안된 경우 -> 저장X
        //2. 첨부파일이 하나라도 수정된 경우 -> 첨부파일 삭제 후 재 등록
        
        List<FileInfo> fileList = fileInfoRepository.findByBoard(newBoard).orElseThrow( ()-> new RuntimeException("해당 게시글의 첨부파일이 없습니다."));
        // log.info("{}", fileList.stream().map(file->file.getFileName())
        //                                         .collect(Collectors.toList()));
        List<String> oldFileList = fileList.stream().map(file->file.getFileName())
                                                        .collect(Collectors.toList());
        log.info("updatefile : {}", updateFileList);
        log.info("oldfilelist : {}", oldFileList);
        // files.stream().map( () -> )
        
        return board;
    }

    //게시글 삭제
    public Board delBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("삭제할 게시글이 없습니다."));
        board.setStatus(BoardStatus.UNREGISTERED).setUnregisteredAt(LocalDateTime.now());
        
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


    private BoardApiResponse response(Board board, List<FileInfo> files){
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                                                            .id(board.getId())
                                                            .title(board.getTitle())
                                                            .content(board.getContent())
                                                            .registeredAt(board.getRegisteredAt())
                                                            .unregisteredAt(board.getUnregisteredAt())
                                                            .createdAt(board.getCreatedAt())
                                                            .createdBy(board.getCreatedBy())
                                                            .status(board.getStatus())
                                                            .build();
        boardApiResponse.setFileInfo(files);
        return boardApiResponse;                                                                
    }

}
