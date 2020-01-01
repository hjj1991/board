package board.member.service;

import java.util.HashMap;
import java.util.List;

import board.member.dto.MemberDto;
import board.member.entity.MemberEntity;

public interface JpaMemberService {
	List<MemberEntity> selectMemberList() throws Exception;
	
	int checkId(String userId) throws Exception;


}