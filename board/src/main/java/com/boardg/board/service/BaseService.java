package com.boardg.board.service;

import com.boardg.board.ifs.CrudInterface;
import com.boardg.board.model.network.Header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService<Req,Res, T> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected JpaRepository<T, Long> baseRepository;
    
}
