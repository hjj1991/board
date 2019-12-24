package board.common;

import lombok.Data;

@Data
public class Pagination {
	
//	private int listSize = 10;				//초기값으로 목록개수를 10으로 셋팅
//	private int rangeSize = 10;				//초기값으로 페이지범위를 10으로 셋팅
	private int page;						//현재 페이지
	private int pageSize;
	private String searchTarget;
	private String searchKeyword;
//	private int range;						//현재 페이지 범위
//	private String next;					//다음페이지
//	private String last;					//마지막 페이지
//	private String first;					//처음페이지
//	private String prev;					//이전페이지



}
