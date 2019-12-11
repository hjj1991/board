package board.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.member.dto.MemberDto;

@Mapper
public interface MemberMapper {

	List<MemberDto> selectMemberList() throws Exception;

	int checkId(String userId) throws Exception;

	void signUp(MemberDto memberDto) throws Exception;

}
