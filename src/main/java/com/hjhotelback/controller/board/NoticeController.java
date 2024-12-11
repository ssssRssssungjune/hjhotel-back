package com.hjhotelback.controller.board;

import com.hjhotelback.dto.board.*;
import com.hjhotelback.service.board.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/notices")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 전체 목록 조회
    @GetMapping
    public ResponseEntity<PageResponse<Notice>> getAllNotices(@RequestParam(name = "page", required = false) Integer page,
                                                              @RequestParam(name = "size", required = false) Integer size,
                                                              @RequestParam(name = "sort_order", required = false) SortOrder sortOrder) {

        PageRequest pageRequest = PageRequest.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 10)
                .sortOrder(sortOrder != null ? sortOrder : SortOrder.DESC)
                .build();

        return ResponseEntity.ok(noticeService.getAllNotices(pageRequest));
    }

    // 공지사항 상세 보기
    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(noticeService.getNoticeByIdForUser(id));
    }
}
