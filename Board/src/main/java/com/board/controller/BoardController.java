package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

//	글쓰기 화면 이동
	@GetMapping(value = "/board/write.do")
//	Request에서 파라미터를 받아오는데 idx라는 name 가져오고, Long 데이터 타입으로 idx라는 이름으로 사용하겠다.
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
//		idx가 널값이면
//		새로운 Dto 객체를 생성하고 
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
//			idx가 널값이 아니면
//			idx를 파라미터로 받는 서비스의 getBoardDetail 게시판 상세 조회 함수를 결과 값을
//			BoardDTO 데이터 타입으로 board라는 변수에 저장한다.
			BoardDTO board = boardService.getBoardDetail(idx);
//			idx로 조회 했는데 가져온 dto가 널값이면 글쓰기가 아니라 목록 화면으로 리턴
			if (board == null) {
				return "redirect:/board/list.do";
			}
//			
			model.addAttribute("board", board);
		}
//		매칭
		return "board/write";
	}
	
	
//	글 등록 화면
//	BoardDTO 유저가 쓴 데이터 값들
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}
		
//		등록이 잘 되면 리스트로 이동
		return "redirect:/board/list.do";
	}

}