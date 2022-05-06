package com.boardg.board.controller;

import com.boardg.board.ifs.CrudInterface;
import com.boardg.board.model.entity.Board;
import com.boardg.board.model.network.Header;
import com.boardg.board.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public abstract class CrudController<Req, Res, T> implements CrudInterface<Req, Res> {

    @Autowired
    protected BaseService<Req, Res, T> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(Header<Req> request) {
        log.info("{}", request);
        return baseService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public Header<Res> read(@PathVariable Long id) {
        log.info("{}",id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(Header<Req> request) {
        
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public Header<Res> delete(@PathVariable Long id) {
        
        return baseService.delete(id);
    }
    
}
