package com.hjhotelback.service.board;

import com.hjhotelback.dto.board.Notice;
import com.hjhotelback.dto.board.PageRequest;
import com.hjhotelback.dto.board.PageResponse;
import com.hjhotelback.dto.board.SortOrder;
import com.hjhotelback.mapper.board.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoticeService {
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    // 사용자페이지에서 사용할 공지사항 상세 조회 서비스
    @Transactional
    public Notice getNoticeByIdForUser(Integer id) {
        // 조회수 증가
        noticeMapper.incrementViews(id);

        return noticeMapper.findById(id).orElseThrow(() -> new NoSuchElementException(id + "번을 찾을 수 없습니다."));
    }
    
    // 관리자페이지에서 사용할 공지사항 상세 조회 서비스
    @Transactional
    public Notice getNoticeByIdForAdmin(Integer id) {
        return noticeMapper.findById(id).orElseThrow(() -> new NoSuchElementException(id + "번을 찾을 수 없습니다."));
    }

    public PageResponse<Notice> getAllNotices(PageRequest pageRequest) {
        // Enum을 String으로 처리
        List<Notice> todos = noticeMapper.findAll(pageRequest.getSortOrder().name(), pageRequest.getOffset(), pageRequest.getSize());

        int totalElements = noticeMapper.countTotal();
        int currentPage = pageRequest.getPage();
        int size = pageRequest.getSize();
        SortOrder sortOrder = pageRequest.getSortOrder();


        return new PageResponse<>(todos, totalElements, currentPage, size, sortOrder);
    }

    public void createNotice(Notice notice) {
        noticeMapper.save(notice);
    }

    public void updateNotice(Notice notice) {
        noticeMapper.update(notice);
    }

    public void deleteNotice(Integer id) {
        noticeMapper.delete(id);
    }
}
