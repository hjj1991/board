package board.board.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.fabric.Server;

import lombok.extern.slf4j.Slf4j;
import board.board.dto.BoardCommentDto;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.dto.RestBoardDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;
import board.common.Pagination;


@Slf4j
@Service
//@Transactional
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//			String name;
//			while(iterator.hasNext()) {
//				name = iterator.next();
//				log.debug("file tag name : "+name);
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//				for(MultipartFile multipartFile : list) {
//					log.debug("start file information");
//					log.debug("file name : " + multipartFile.getOriginalFilename());
//					log.debug("file size : " + multipartFile.getSize());
//					log.debug("file content type : " + multipartFile.getContentType());
//					log.debug("end file information. \n");
//				}
//			}
//		}
		
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		boardMapper.updateHitCount(boardIdx);
		//int i = 10 /0 ;
		
		
		return board;
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
		
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
		
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {		
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}

	@Override
	public RestBoardDto selectBoardListApi(Pagination pagination) throws Exception {
		
		// TODO Auto-generated method stub
		RestBoardDto restBoardDto = new RestBoardDto();
		int startNum = (pagination.getPage() -1) * pagination.getPageSize();
		int endNum = pagination.getPageSize();
		String searchTarget = pagination.getSearchTarget();
		String searchKeyword = pagination.getSearchKeyword();
		int pageCount = (int)Math.ceil(boardMapper.getBoardListCnt(searchTarget, searchKeyword)/pagination.getPageSize()); //총페이지 수
		HashMap<String, String> links = new HashMap<String, String>();
		String baseUrl = "http://localhost:8080/api/board?";
		
		if(1 < pagination.getPage()) {
			links.put("prev", baseUrl + "page=" + (pagination.getPage() - 1) + "&pageSize=" + pagination.getPageSize());
		}
		if(pageCount > pagination.getPage()) {
			links.put("next", baseUrl + "page=" + (pagination.getPage() + 1) + "&pageSize=" + pagination.getPageSize());
		}
		
		
		restBoardDto.setResults(boardMapper.selectBoardListApi(startNum, endNum, searchTarget, searchKeyword));
		restBoardDto.setPageCount(pageCount);
		restBoardDto.setLinks(links);
//		restBoardDto.setFirst("");
//		restBoardDto.setLast("");
//		restBoardDto.setNext("");
//		restBoardDto.setPrev("");
		
		return restBoardDto;
	}

	@Override
	public BoardCommentDto selectCommentList(int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectCommentList(boardIdx);
	}
}
