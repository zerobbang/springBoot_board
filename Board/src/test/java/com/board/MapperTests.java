package com.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper;

	
	@Test
	public void testOfInsert() {
		
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setWriter("테스터");

		int result = boardMapper.insertBoard(params);
		System.out.println("결과는 " + result + "입니다.");
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			// String boardJson = new ObjectMapper().writeValueAsString(board);
            String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		// given when then 
		//BoardDTO의 데이터를 다른 값으로 수정하는 mapper의 updateBoard가 잘 동작하는지
		
		BoardDTO params = new BoardDTO();
		params.setIdx((long)1);
		params.setTitle("테스트 함수에서 변경 제목");
		params.setContent("테스트 함수에서 변경 내용");
		params.setWriter("zero");
		
		// when
		boardMapper.updateBoard(params);
		
		// null같이 제약 조건에 위배되면 에러가 된다.
		
	}
	
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				//String boardJson = new ObjectMapper().writeValueAsString(board);
                String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testMultipleInsert() {
		// 한 번에 다량의 데이터 넣기
		// insertBoard을 반복 으로 돌려주기
		for(int i=2; i<50;i++) {
			BoardDTO params = new BoardDTO();
			params.setIdx((long)i);
			params.setTitle("테스트 함수에서 변경 제목"+i);
			params.setContent("테스트 함수에서 변경 내용"+i);
			params.setWriter("zero"+i);
			
			boardMapper.insertBoard(params);
			// 이대로 하면 idx가 자동 증가라서 에러가 발생 한 것
			// 이라고 예상했는데 idx왜?
		}
	}

}