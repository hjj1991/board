package board.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import board.api.advice.exception.CUserNotFoundException;
import board.member.dto.MemberDto;
import board.member.entity.MemberEntity;
import board.member.repository.JpaMemberRepository;


@Service
public class JpaMemberServiceImpl implements JpaMemberService, UserDetailsService {

	@Autowired
	private JpaMemberRepository jpaMemberRepository;

	@Override
	public UserDetails loadUserByUsername(String userId)  {
		// TODO Auto-generated method stub
		return jpaMemberRepository.findByUserId(String.valueOf(userId)).orElseThrow(CUserNotFoundException::new);
	}
	@Override
	public List<MemberEntity> selectMemberList() throws Exception {
		return jpaMemberRepository.findAllByOrderByUserIdAsc();
	}
	@Override
	public int checkId(String userId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


}
