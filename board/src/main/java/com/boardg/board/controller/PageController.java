package com.boardg.board.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardg.board.model.dto.BoardDto;
import com.boardg.board.model.entity.Board;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;
import com.boardg.board.service.BoardLogicService;
import com.boardg.board.service.page.PageLogicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/boardPage")
public class PageController {
    
    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PageLogicService pageLogicService;

    @GetMapping("/main")
    public ModelAndView boardList(){

        return new ModelAndView("/board/list").addObject("boardList", pageLogicService.getBoardList());
    }

    @GetMapping("/{id}")
    public ModelAndView boardDetail(@PathVariable Long id){
        // Board board = pageLogicService.getBoardDetail(id);
        // List<Object[]> result = pageLogicService.getBoardDetail(id);
        BoardApiResponse boardApiResponse = pageLogicService.getBoardDetail(id);
        log.info("controller : {}", boardApiResponse);

        //리펙토링.... DTO에 전부 담아 타임리프 VIew에서 파일리스트만 따로 반복문 돌릴수는 없나...
        return new ModelAndView("/board/view").addObject("boardDetail", boardApiResponse)
                                                        .addObject("fileList", boardApiResponse.getFileInfo());
                                                        
    }

    @GetMapping("/write/{id}")
    public ModelAndView write(@PathVariable Long id){
        //id값 null일때 생성
        if(id==0){
            //리펙토링 필요.
            //빈 객체 리턴
            return new ModelAndView("/board/write").addObject("boardDetail", new Board());
        }
        // return new ModelAndView("/board/write").addObject("board", pageLogicService.getBoardDetail(id));
        
        //id값 있을때 수정할 view 리턴
        
        return new ModelAndView("/board/update")
                        .addObject("boardDetail", boardLogicService.read(id));
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false, name = "title") String title){

        return new ModelAndView("/board/list").addObject("boardList", pageLogicService.getBoardKeywordList(title));
    }

    
    @PostMapping("")
    public ModelAndView create(@ModelAttribute("boardDetail") Board newBoard, @RequestParam("uploadfiles") List<MultipartFile> files) throws Exception{
        pageLogicService.createBoard(newBoard, files);
        return new ModelAndView("redirect:boardPage/main");
    }

    @PutMapping("")
    public ModelAndView put(@ModelAttribute Board newBoard, @RequestParam("uploadfiles") List<MultipartFile> files) throws Exception{
        Board board = pageLogicService.updateBoard(newBoard, files);
        return new ModelAndView("/board/view").addObject("boardDetail", pageLogicService.getBoardDetail(board.getId()));
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable Long id){
        // boardLogicService.delete(id);
        
        return new ModelAndView("redirect:main").addObject("board", pageLogicService.delBoard(id));
    }

}
