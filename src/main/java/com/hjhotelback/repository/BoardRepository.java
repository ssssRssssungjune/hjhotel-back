package com.hjhotelback.repository.board;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.mapper.board.BoardRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BoardRepository는 데이터베이스와 직접 상호작용하여
 * SQL을 실행하고 결과를 반환하는 역할을 합니다.
 */
@Repository
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    // JdbcTemplate을 생성자로 주입받습니다.
    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 게시글 저장
     * @param board 저장할 게시글 데이터
     */
    public void save(BoardDTO board) {
        String sql = "INSERT INTO board (title, content, author, is_notice) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, board.getTitle(), board.getContent(), board.getAuthor(), board.isNotice());
    }

    /**
     * 게시글 목록 조회 (페이징 처리)
     * @param offset 시작 위치
     * @param limit 조회할 개수
     * @return 게시글 목록
     */
    public List<BoardDTO> findAll(int offset, int limit) {
        String sql = "SELECT * FROM board ORDER BY created_at DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BoardRowMapper(), limit, offset);
    }

    /**
     * 특정 게시글 조회
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 데이터
     */
    public BoardDTO findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BoardRowMapper(), id);
    }

    /**
     * 게시글 수정
     * @param board 수정할 게시글 데이터
     */
    public void update(BoardDTO board) {
        String sql = "UPDATE board SET title = ?, content = ?, is_notice = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, board.getTitle(), board.getContent(), board.isNotice(), board.getId());
    }

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글 ID
     */
    public void delete(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 게시글 검색
     * @param keyword 검색 키워드
     * @param offset 시작 위치
     * @param limit 조회할 개수
     * @return 검색된 게시글 목록
     */
    public List<BoardDTO> search(String keyword, int offset, int limit) {
        String sql = "SELECT * FROM board WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        String searchKeyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new BoardRowMapper(), searchKeyword, searchKeyword, limit, offset);
    }
}
