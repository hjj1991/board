package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.board.dto.BoardCommentDto;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.dto.RestBoardDto;
import board.common.Pagination;


@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;

	void insertBoard(BoardDto board) throws Exception;

	void updateHitCount(int boardIdx) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	int updateBoard(BoardDto board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;

	void insertBoardFileList(List<BoardFileDto> list) throws Exception;

	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;

	BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
	int getBoardListCnt(@Param("searchTarget") String searchTarget, @Param("searchKeyword") String searchKeyword) throws Exception;

	List<BoardDto> selectBoardListApi(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("searchTarget") String searchTarget, @Param("searchKeyword") String searchKeyword) throws Exception;

	BoardCommentDto selectCommentList(int boardIdx) throws Exception;
	
}
