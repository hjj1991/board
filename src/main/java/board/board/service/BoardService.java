package board.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardCommentDto;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.dto.RestBoardDto;
import board.common.Pagination;


public interface BoardService {
	
	List<BoardDto> selectBoardList() throws Exception;

	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(BoardDto board) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;

	RestBoardDto selectBoardListApi(Pagination pagination) throws Exception;

	BoardCommentDto selectCommentList(int boardIdx) throws Exception;
	
}
