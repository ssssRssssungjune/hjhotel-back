 package com.hjhotelback.service.board;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
 public class BoardService {
    private final BoardRepository boardRepository;
    
    public BoardService(BoardRepository boardRepository) {
    	this.boardRepository = boardRepository;
    }
    
    public BoardDTO createBoard(BoardDTO boardDTO) {
    	//게시글 저장 로직
    	Board board = new Board();
    	board.setTitle(boardDTO.getTitle());
    	board.setContent(boardDTO.getContent());
    	board.setAuthor(boardDTO.getAuthor());
    	board.setCreatedAt(LocalDateTime.now());
    	return BoardMapper.toDTO(boardRepository.save(board));
    	
    }
    
    public List<BoardDTO>getAllBoard(int page){
    	//페이징 처리 로직 
    	Pageable pageable = PageRequest.of(page - 1,10);//페이지 크기 :10
         Page<Board> boards = boardRepository.findAll(pageable);
         return boards.getContent().stream()
        		 .map(BoardMapper::toDTO)
        		 .collect(Collectors.toList());
    	}
    }
 }
