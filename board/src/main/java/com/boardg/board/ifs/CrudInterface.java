package com.boardg.board.ifs;


import com.boardg.board.model.network.request.BoardApiRequest;
import com.boardg.board.model.network.response.BoardApiResponse;

public interface CrudInterface<Req,Res> {
    
    public BoardApiResponse create(BoardApiRequest request);

    public BoardApiResponse read(Long id);

    public BoardApiResponse update(BoardApiRequest request);

    public BoardApiResponse delete(Long id);
}
