package com.boardg.board.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardg.board.model.entity.Board;
import com.boardg.board.model.network.response.BoardApiResponse;
import com.boardg.board.repository.BoardRepository;
import com.boardg.board.service.BoardLogicService;
import com.boardg.board.service.page.PageLogicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/index")
    public ModelAndView index(){
        // log.info(">>>>>>>>>>> {}", boardLogicService.read(70L));
        BoardApiResponse boardaApiResponse =  boardLogicService.read(70L).getData();
        log.info("boardresponse : {}",boardaApiResponse);
        return new ModelAndView("/page/main")
                    .addObject("lists",boardaApiResponse);
                        
    }

    @GetMapping("/main")
    public ModelAndView boardList(){

        return new ModelAndView("/board/list").addObject("boardList", pageLogicService.getBoardList());
    }

    @GetMapping("/{id}")
    public ModelAndView boardDetail(@PathVariable Long id){
        return new ModelAndView("/board/view").addObject("boardDetail", pageLogicService.getBoardDetail(id));
    }

    @GetMapping("/write")
    public ModelAndView write(@PathVariable(required = false) Long id){
        if(id==null){
            //리펙토링 필요.
            return new ModelAndView("/board/write").addObject("board", new Board());
        }
        return new ModelAndView("/board/write").addObject("board", pageLogicService.getBoardDetail(id));
    }
    // .addObject("board", pageLogicService.getBoardDetail(70L))

    @PostMapping("")
    public ModelAndView create(@ModelAttribute Board newBoard){
        Long id = boardLogicService.create(pageLogicService.addHeader(newBoard)).getData().getId();

        return new ModelAndView("/board/view").addObject("board", pageLogicService.getBoardDetail(id));
    }

}
