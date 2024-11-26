package com.hjhotelback.mapper.board;

import java.sql.ResultSet;

import com.hjhotelback.dto.board.BoardDTO;


/**
 * BoardRowMapper는 SQL결과 (Resultset)를 BoardDTO객체로 매핑합니다
 * 데이터베이스의 각 행(ROW)DMF BoardDTO로 변환 합니다
 */
public class BoardRowMapper implements RoWMapper<BoardDTO> {
	
	@Override

	public  BoardDTO mapRow(ResultSet rs, int rowNum)throws SQLException {
		
		BoardDTO board = new BoardDTO();
		board.setId(rs.getLong("id")); //ID매핑
		board.setTitle(rs.getString("title")); // 제목 매핑
		board.setContent(rs.getString("content"));//내용 매핑
		board.setAuthor(rs.getString("author"));//작성자 매핑
		board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		board.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
		board.setNotice(rs.getBoolean("is_notice"));//공지여부매핑 
		return board;
	}

}
