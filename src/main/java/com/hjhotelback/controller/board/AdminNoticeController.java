package com.hjhotelback.controller.board;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.board.Notice;
import com.hjhotelback.dto.board.NoticeRequest;
import com.hjhotelback.dto.board.PageRequest;
import com.hjhotelback.dto.board.PageResponse;
import com.hjhotelback.dto.board.SortOrder;
import com.hjhotelback.service.board.NoticeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/notices")
public class AdminNoticeController {
	
	private final NoticeService noticeService;

    public AdminNoticeController(NoticeService noticeService) {
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
	
	// 공지사항 생성
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
    
    // 공지사항 상세 보기
    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(noticeService.getNoticeByIdForAdmin(id));
    }

    // 공지사항 상세 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable("id") Integer id, @RequestBody @Valid NoticeRequest noticeRequest) {
        Notice notice = noticeService.getNoticeByIdForAdmin(id);
        notice.setNoticeId(id);
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setCategory(noticeRequest.getCategory());
        notice.setIsImportant(noticeRequest.getIsImportant());

        noticeService.updateNotice(notice);
        return ResponseEntity.ok("공지사항 수정 완료");
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable("id") Integer id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("공지사항 삭제 완료");
    }
}
