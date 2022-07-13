package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

//	게시글 등록 함수
	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;

//		만약 Controller에서 넘어올 idx가 없으면 글 생성 / 있다면 글 수정을 진행한다.
		if (params.getIdx() == null) {
			queryResult = boardMapper.insertBoard(params);
		} else {
			queryResult = boardMapper.updateBoard(params);
		}

		BoardDTO board = null;
		System.out.println(board.getTitle());

		return (queryResult == 1) ? true : false;
	}

//	게시글 상세 조회 함수
	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}

//	게시글 삭제 함수
	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;

		BoardDTO board = boardMapper.selectBoardDetail(idx);

		if (board != null && "N".equals(board.getDeleteYn())) {
			queryResult = boardMapper.deleteBoard(idx);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> boardList = Collections.emptyList();

		int boardTotalCount = boardMapper.selectBoardTotalCount();

		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList();
		}

		return boardList;
	}

}