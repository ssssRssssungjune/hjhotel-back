 package com.hjhotelback.dto.board;



 @Data
 public class BoardDto {
     private Long id;  // 게시글 id
     private String title;  // 제목
     private String content;  //내용
     private String author;   // 작성자
     private String createdAt; //직상일
     private String updatedAt;  // 수정일
    

 }
