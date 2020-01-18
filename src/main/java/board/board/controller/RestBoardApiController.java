package board.board.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.board.dto.BoardCommentDto;
import board.board.dto.BoardDto;
import board.board.dto.RestBoardDto;
import board.board.service.BoardService;
import board.common.Pagination;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class RestBoardApiController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/api/board", method = RequestMethod.GET)
	public RestBoardDto openBoardList(@ModelAttribute Pagination pagination) throws Exception {
//		return boardService.selectBoardList();
		return boardService.selectBoardListApi(pagination);
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
	public ResponseEntity<?> insertBoard(@RequestBody BoardDto board, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			HashMap<String, String> errorList = new HashMap<String, String>();
			for (FieldError error : errors) {
				errorList.put(error.getField(), error.getDefaultMessage());
				// System.out.println (error.getField() + " - " + error.getDefaultMessage());
			}
			return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
		}
		boardService.insertBoard(board, null);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	// @RequestMapping(value="/api/board/", method=RequestMethod.GET) //url
	// /api/board/?boardIdx=?? 이런형식
	// public BoardDto openBoardDetail(@RequestParam("boardIdx") int boardIdx)
	// throws Exception{
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board";
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}

	// 댓글관련 Controller
	@RequestMapping(value = "/api/board/comment/{boardIdx}", method = RequestMethod.GET)
	public BoardCommentDto openComentList(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectCommentList(boardIdx);
	}
}
