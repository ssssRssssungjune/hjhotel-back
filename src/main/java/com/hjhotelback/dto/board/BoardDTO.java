 package com.hjhotelback.dto.board;

import java.time.LocalDateTime;

/**
 * BoardDTO는 데이터 전송을 위한 객체입니다.
 * 이 클래스는 게시글 데이터를 담아 클라이언트와 서버 간에 전달됩니다.
 */
public class BoardDTO {
    private Long id;  // 게시글 ID
    private String title;  // 제목
    private String content;  // 내용
    private String author;  // 작성자
    private LocalDateTime createdAt; // 작성일
    private LocalDateTime updatedAt; // 수정일
    private boolean isNotice;//공지사항 여부


    //기본 생성자
    public 	BoardDTO() {}
    
    //모든 필드를 초기화 하는 생성자
    
      // Getter와 Setter는 데이터를 읽고 쓸 수 있도록 제공합니다.
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public boolean isNotice() {
        return isNotice;
    }
    public void setNotice(boolean isNotice) {
        this.isNotice = isNotice;
    }
}