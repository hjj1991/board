package board.member.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String userId;
	private String userPw;
	private String name;
	private String nickName;
	private String emailAddr;
	private String tel;
	private String createDate;
	private String loginDate;
}
