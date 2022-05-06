package com.boardg.board.model.network;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unchecked")
public class Header<T> {

    private LocalDateTime transactionTime;

    private String resultCode;

    private String description;

    private T data;

    // private Pagenation Pagenation;

    //OK
    public static<T> Header<T> OK(){
        return (Header<T>)Header.builder()
                            .transactionTime(LocalDateTime.now())
                            .resultCode("OK")
                            .description("OK")
                            .build();
    }

    //DATA OK
    public static<T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                                .transactionTime(LocalDateTime.now())
                                .resultCode("OK")
                                .description("OK")
                                .data(data)
                                .build();
    }

    // pagenation
    // public static<T> Header<T> OK(T data, Pagenation pagenation){
    //     return (Header<T>)Header.builder()
    //                         .transactionTime(LocalDateTime.now())
    //                         .resultCode("OK")
    //                         .description("OK")
    //                         .pagenation(pagenation)
    //                         .data(data)
    //                         .build();
    // }

    public static<T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                                .transactionTime(LocalDateTime.now())
                                .resultCode("ERROR")
                                .description(description)
                                .build();
    }

    
}
