package board.board.dto;

import lombok.Data;

@Data
public class BoardCommentDto {
	private int commentNum;
	private int commentBoardIdx;
	private String commentContents;
	private String commentCreatorId;
	private String commentCreatedDatetime;
}
