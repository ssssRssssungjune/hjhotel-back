package com.hjhotelback.controller.board;

import com.hjhotelback.dto.board.*;
import com.hjhotelback.service.board.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Integer id) {
        return ResponseEntity.ok(noticeService.getNoticeById(id));
    }

    @PostMapping
    public ResponseEntity<String> createNotice(@RequestBody @Valid NoticeRequest noticeRequest) {
        Notice notice = new Notice();
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setCategory(noticeRequest.getCategory());
        notice.setIsImportant(noticeRequest.getIsImportant());

        noticeService.createNotice(notice);
        return ResponseEntity.ok("공지사항 추가 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable Integer id, @RequestBody @Valid NoticeRequest noticeRequest) {
        Notice notice = noticeService.getNoticeById(id);
        notice.setNoticeId(id);
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setCategory(noticeRequest.getCategory());
        notice.setIsImportant(noticeRequest.getIsImportant());

        noticeService.updateNotice(notice);
        return ResponseEntity.ok("공지사항 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Integer id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("공지사항 삭제 완료");
    }
}
