package com.boardg.board.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.boardg.board.model.enumclass.BoardStatus;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = "fileInfoList")             //필드명... 클래스 명이 아님....
// @EqualsAndHashCode(exclude = "id")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<FileInfo> fileInfoList = new ArrayList<>();
    
    

    // @Builder
    // public Board(Long id, String title, String content, BoardStatus status, LocalDateTime registeredAt, LocalDateTime unregisteredAt){
    //     this.id =id;
    //     this.title = title;
    //     this.content = content;
    //     this.status = status;
    //     this.registeredAt = registeredAt;
    //     this.unregisteredAt = unregisteredAt;
    // }
    
}
