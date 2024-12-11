package com.hjhotelback.dto.board;

import com.hjhotelback.dto.board.SortOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;


@Getter
@Setter
@Builder
public class PageRequest {

    private int page;  // 기본 페이지는 1

    private int size; // 기본 페이지 크기는 10

    private SortOrder sortOrder;

    public int getOffset() {
        return (page - 1) * size;
    }
}