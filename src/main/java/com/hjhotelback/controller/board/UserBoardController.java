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
@RequestMapping("/api/boards")
public class UserBoardController {

    private final BoardService boardService;

    public UserBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 일반 게시글 목록 조회
    @GetMapping
    public String getAllBoards(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        List<BoardDTO> boards = boardService.getAllBoards(page, size);
        model.addAttribute("boards", boards);
        model.addAttribute("page",page);
        return "boards/list";
    }

    // 일반 게시글 작성 폼
    @GetMapping("/create")
    public String createBoardForm() {
        return "boards/create";
    }

    // 일반 게시글 작성
    @PostMapping
    public String createBoard(BoardDTO board) {
        boardService.createBoard(board);
        return "redirect:/api/boards";
    }

    // 일반 게시글 상세보기
    @GetMapping("/{id}")
    public String getBoardById(@PathVariable Long id, Model model) {
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "user/detail";
    }
}
