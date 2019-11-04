package board.board.dto;
import lombok.Data;
@Data
public class BoardDto {
	private int boardIndx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
}
