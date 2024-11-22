package com.hjhotelback.controller.board;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.service.board.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    
    public BoardController (BoardService boardService) {
    	this.boardService = boardService;
    }
    
    /**
     * 공지사항 작성 기능
     * 관지라만 작성 기능
     */
    
    @Secured("ROLE_ADMIN")//관리자만 접근 가능
    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO){
    	if(boardDTO.getTitle() == null || boardDTO.getContent() == null) {
    		throw new IllegalArgumentException("필수 입력 항목");
    	}
    	return ResponseEntity.ok(boardService.createBoard(boardDTO));
    }
    /**
     * 게시글 전체 조회 (페이징 처리 포함)
     */
    
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards(@RequestParam(defaultValue = "1")int page){
    	return ResponseEntity.ok(boardService.getAllBoards(page));
    }
    /**
     * 게시글 상세보기
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id){
    	return ResponseEntity.ok(boardService.getBoardById(id));
    }
    /**
     * 게시글 수정
     */
    
    @Secured("ROLE_ADMIN")//관리자만 수정 가능
    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
    	if(boardDTO.getTitle()== null || boardDTO.getContent() == null) {
    		throw new IllegalArgumentException(" 필수 입력");
    	}
    	return ResponseEntity.ok(boardService.updateBoard(id, boardDTO));
    }
    /**
     * 게시글 삭제
     */
    @Secured("ROLE_ADMIN")//관리자만 삭제 가능
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
    	boardService.deleteBoard(id);
    	return ResponseEntity.noContent().build();
    }
    /**
     * 게시글 검색 기능(제목, 내용검색)
     */
    
    @GetMapping("/search")
    public ResponseEntity<List<BoardDTO>>searchBoards(
    		@RequestParam(required = false) String keyword,
    		@RequestParam(defaultValue = "1") int page){
    	return ResponseEntity.ok(boardService.searchBoards(keyword,page));
    }
    

}
