 package com.hjhotelback.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.mapper.board.BoardMapper;

/**
 * BoardService는 게시글 데이터를 처리하나느 비즈니스 로직을 담당하는 클래스
 * 데이터 베이스에서 테이터를 조회하거나 특정 조건에 따라 데이터를 가공합니다
 */

@Service
 public class BoardService {

	@Autowired
	private BoardMapper boardMapper; //BoardMapper는데이터베이스와 상호작용하는 Mapper객체 입니다
	
	
	//모든 공지사할 조회
  public List<BoardDTO> getAllBoards(){
	  return boardMapper.getAllBoards();
  }
  // 특정 게시글 조회 메서드
  public BoardDTO getboardById(Long id) {
	  //Repository 또ㅡㄴ 데이ㅓㅌ 소스에서 iD로 게시글 조회
	  return boardMapper.getNoticeById(id);
  }
  
  //공지사항 수정
  
  public void updateNotice(BoardDTO boardDTO) {
	  boardMapper.updateNotice(boardDTO);
  }
  
  //공지사항생성
  public void createNotice(BoardDTO boardDTO) {
	  boardMapper.createNotice(boardDTO);
  }
}