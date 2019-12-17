package board.board.dto;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class RestBoardDto {
	private int pageCount;					//총 페이지수
//	private String next;					//다음페이지
//	private String prev;					//이전페이지
	private HashMap<String, String> links;
	private List<BoardDto> results;			//게시물
	
	
}
