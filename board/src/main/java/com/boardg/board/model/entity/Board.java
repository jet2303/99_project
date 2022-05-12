package com.boardg.board.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
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



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<FileInfo> fileList;

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
