package board.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.member.dto.MemberDto;
import board.member.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public List<MemberDto> selectMemberList() throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.selectMemberList();
	}

	@Override
	public int checkId(String userId) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.checkId(userId);
	}

}
