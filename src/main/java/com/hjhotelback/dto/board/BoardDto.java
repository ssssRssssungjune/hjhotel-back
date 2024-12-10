 package com.hjhotelback.dto.board;

 import java.time.LocalDateTime;

 /**
  * BoardDTO는 공지사항 데이터를 전달하기 위한 객체입니다.
  * 이 클래스는 클라이언트와 서버 간에 데이터를 주고받기 위해 사용됩니다.
  */
 public class BoardDTO {
     private Long noticeId; // 공지사항 ID
     private String title; // 공지사항 제목
     private String content; // 공지사항 내용
     private String category; // 공지사항 분류 (NOTICE, EVENT, INFO)
     private boolean isImportant; // 중요 공지 여부
     private Integer views; // 조회수
     private LocalDateTime createdAt; // 게시글 생성 일시
     private LocalDateTime updatedAt; // 게시글 마지막 수정일시
    
     /**
      * *전체 필드를 초기화 하는 생성자입니다
      * @param noticeId 게시글 ID
      * @param title 게시글 제목
      * @param content 게시글 내용
      * @param category 게시글 카테고리
      * @param isImportant 중요게시글 여부
      * @param views 조회수
      * @param createdAt 생성 일시
      * @param updatedAt 수정 일시
      */
     
     public BoardDTO(Long noticeId, String title, String content, String category,
    		          boolean isImportant, Integer views , LocalDateTime createdAt, LocalDateTime updatedAt) {
    	 this.noticeId = noticeId;
    	 this.title = title;
    	 this.content = content;
    	 this.category = category;
    	 this.isImportant = isImportant;
    	 this.views = views;
    	 this.createdAt = createdAt;
    	 this.updatedAt = updatedAt;
     }
	
     public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isImportant() {
		return isImportant;
	}
	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
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

     

 }
