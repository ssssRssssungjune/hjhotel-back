package com.hjhotelback.dto.board;

import com.hjhotelback.dto.board.SortOrder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponse<T> {
    private final List<T> content;  // 페이징된 콘텐츠 목록
    private final int totalElements; // 전체 데이터 개수
    private final int totalPages;    // 전체 페이지 수
    private final int currentPage;   // 현재 페이지
    private final int size;          // 페이지 크기
    private final SortOrder sortOrder;        // 정렬기준

    public PageResponse(List<T> content, int totalElements, int currentPage, int size, SortOrder sortOrder) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.sortOrder = sortOrder;
    }
}