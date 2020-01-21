package board.board.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BoardRequestDto {
	private int boardType;
	@JsonIgnore
	private int boardIdx;
	private String title;
	private String contents;
	@JsonIgnore
	private int hitCnt;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private String createdDatetime;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private String updatedDatetime;
	private List<BoardFileDto> fileList;
	private String authToken;
}
