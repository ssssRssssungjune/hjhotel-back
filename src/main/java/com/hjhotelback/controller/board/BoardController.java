package com.hjhotelback.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.service.board.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
	
	//모든 게시글 조회
	@Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardDTO> getAllBoards() {
        return boardService.getAllBoards();
    }
    
      //특정 게시글 조회
       @GetMapping("/{id}")
        public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id){
    	BoardDTO board = boardService.getboardById(id);
    	if(board != null) {
    		return ResponseEntity.ok(board); //호회 성공시 데이터 반환	
    	} else {
    		return ResponseEntity.notFound().build(); // 해당 ID의 게시글아 없을 경우 404 반환 
    	}
       }
    	
    	//공지사항 수정
    	@PutMapping("/{id}")
    	public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
    		try {
    			boardDTO.setNoticeId(id);//ID 설정
    			boardService.updateNotice(boardDTO); // 수정요청
    			return ResponseEntity.ok("게시글이 성공적으로 수정됨");
    		} catch(Exception e) {
    			return ResponseEntity.badRequest().body("게시글 수정에 실패");
    		}
    	}
    	//공지사항 생성
    	@PostMapping
    	public ResponseEntity<String> createBoard(@RequestBody BoardDTO boardDTO){
    		try {
    			boardService.createNotice(boardDTO);// 생성요청
    			return ResponseEntity.ok("게시글이 성공적으로 생성");
    		}catch(Exception e){
    			return ResponseEntity.badRequest().body("게시글 생성실패");
    		}
    		
    	}
    	
       }
    	
    			
    			
 