package com.hjhotelback.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.board.BoardDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> getAllBoards(); //모든 공지사항 조회
	BoardDTO getNoticeById(Long id); //특정 공지사항 조회
	void updateNotice(BoardDTO boardDTO); //공지사항 수정
	void createNotice(BoardDTO boardDTO); //공지사항 생성
}
