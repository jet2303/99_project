package com.boardg.board.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;
import com.boardg.board.service.BoardLogicService;
import com.boardg.board.service.page.PageLogicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Board board = pageLogicService.getBoardDetail(id);
        return new ModelAndView("/board/view").addObject("boardDetail", board);
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

    
    @PostMapping("")
    public ModelAndView create(@ModelAttribute Board newBoard){
        return new ModelAndView("redirect:boardPage/main").addObject("boardDetail", pageLogicService.createBoard(newBoard));
    }

    @PutMapping("")
    public ModelAndView put(@ModelAttribute Board newBoard){
        Board board = pageLogicService.updateBoard(newBoard);
        return new ModelAndView("/board/view").addObject("boardDetail", pageLogicService.getBoardDetail(board.getId()));
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable Long id){
        // boardLogicService.delete(id);
        
        return new ModelAndView("redirect:main").addObject("board", pageLogicService.delBoard(id));
    }

}
