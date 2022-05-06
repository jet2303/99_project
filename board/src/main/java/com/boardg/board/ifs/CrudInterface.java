package com.boardg.board.ifs;

import com.boardg.board.model.network.Header;

public interface CrudInterface<Req,Res> {
    
    public Header<Res> create(Header<Req> request);

    public Header<Res> read(Long id);

    public Header<Res> update(Header<Req> request);

    public Header<Res> delete(Long id);
}
