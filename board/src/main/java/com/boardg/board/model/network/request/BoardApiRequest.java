package com.boardg.board.model.network.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.boardg.board.model.entity.FileInfo;
import com.boardg.board.model.enumclass.BoardStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardApiRequest {
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    // private List<FileInfo> fileInfo;
}
