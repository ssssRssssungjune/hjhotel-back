package com.hjhotelback.controller.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hjhotelback.dto.board.BoardDTO;
import com.hjhotelback.service.board.BoardService;

@Controller
@RequestMapping("/admin/boards")
public class AdminBoardController{
	
	private final BoardService boardService;
	
	public AdminBoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	 // 1. 게시글 목록 조회
    @GetMapping
    public String getAllBoards(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        List<BoardDTO> boards = boardService.getAllBoards(page, size);
        model.addAttribute("boards", boards);
        model.addAttribute("page", page);
        return "admin/list";
    }

    // 2. 공지사항 작성 폼
    @GetMapping("/create")
    public String createBoardForm() {
        return "admin/create";
    }

    // 2. 공지사항 작성 (리다이렉트)
    @PostMapping
    public String createBoard(BoardDTO board) {
        boardService.createBoard(board);
        return "redirect:/admin/boards";
    }

    // 3. 게시글 상세보기
    @GetMapping("/{id}")
    public String getBoardById(@PathVariable Long id, Model model) {
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "admin/detail";
    }

    // 4. 게시글 수정 폼
    @GetMapping("/update/{id}")
    public String updateBoardForm(@PathVariable Long id, Model model) {
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "admin/update";
    }

    // 4. 게시글 수정 (리다이렉트)
    @PostMapping("/update/{id}")
    public String updateBoard(@PathVariable Long id, BoardDTO board) {
        boardService.updateBoard(id, board);
        return "redirect:/admin/boards";
    }

    // 5. 게시글 삭제
    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/admin/boards";
    }
}
	
}
