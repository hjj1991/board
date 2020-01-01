package board.member.service;

import java.util.HashMap;
import java.util.List;

import board.member.dto.MemberDto;
import board.member.entity.MemberEntity;

public interface MemberService {

	List<MemberDto> selectMemberList() throws Exception;

	int checkId(String userId) throws Exception;

	void signUp(MemberDto memberDto) throws Exception;
	


}
