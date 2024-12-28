package com.hjhotelback.dto.member.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {
    private List<T> content;     // 실제 데이터
    private int totalElements;   // 전체 데이터 개수
    private int totalPages;      // 전체 페이지 개수
    private int size;            // 페이지 크기
    private int number;          // 현재 페이지
}
