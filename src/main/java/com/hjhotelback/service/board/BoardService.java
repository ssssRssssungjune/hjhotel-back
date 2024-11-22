 package com.hjhotelback.service.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.repository.BoardRepository;
/**
 * BoardService 클래스는 비즈니스 로직을 처리하며 
 * Repository와 Controller사이에서 데이터를 중계한다
 */
@Service
 public class BoardService {
    private final BoardRepository boardRepository;
  //BoardRepository를 생성자로 주입 받음
    public BoardService(BoardRepository boardRepository) {
    	this.boardRepository = boardRepository;
    }
    
    /**
     * 게시글 생성 
     * @param board생성할 게시글 데이터
     * 
     */
    public void createBoard(BoardDTO board) {
    	boardRepository.save(board);//게시글 저장
    }
    
    /**
     * 게시글 목록 조회 
     * @param page 조회할 페이지 번호(1부터 시작)
     * @param siZE페이지당 게시글 개수
     * @return 조회도니 게시글 목록
     */
    
    public List<BoardDTO>getAllBoards(int page,int size){
    	//페이징 처리 로직 
    	int offset = (page -1) * size;
      return boardRepository.findAll(offset,size);//페이지에 해당하는 게시를목록 반환
    	}
    /**
     * 게시글 수정
     * @param ID 조회할 게시글 ID
     * @return board 수정할데이터
     */

    public  void updateBoard (Long id,BoardDTO board) {
        board.setId(id);
        boardRepository.update(board);
    }
    
    /**
     * 게시글 삭제
     * @param ID 삭제할 게시글 ID
     */

    public  void deleteBoard (Long id) {
        boardRepository.delete(id);
    }

    /**
     * 게시글 검색
     * @param keyword 검색 키워드
     * @param page 페이지 번호
     * @param size 페이지당 게시글 게수
     * @return 검색된게시글 목록
  
     */

    

    public List<BoardDTO> searchBoards(String keyword, int page,int size){
    	int offset = (page -1) *size;
        return boardRepository.search(keyword,offset, size);키워드로 게시글 검색
}
}
