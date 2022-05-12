package com.boardg.board.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@RequiredArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long boardIdx;

    private String fileName;

    private String filePath;

    @ManyToOne
    private Board board;    

    public FileInfo(String fileName, String filePath){
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
