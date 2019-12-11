package board.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.board.dto.BoardDto;
import board.member.dto.MemberDto;
import board.member.service.MemberService;

@RestController
public class RestMemberApiController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/api/member", method=RequestMethod.GET)
	public List<MemberDto> openMemberList() throws Exception{
		return memberService.selectMemberList();
	}

	@RequestMapping(value="/api/member/checkId", method=RequestMethod.GET)
	public int checkId(@RequestParam("userId") String userId) throws Exception{
		return memberService.checkId(userId);
	}
	
	@RequestMapping(value="/api/member/signup", method=RequestMethod.PUT)
	public String signUp(@RequestBody MemberDto memberDto) throws Exception{
		memberService.signUp(memberDto);
		return "{\"status\": 200}";
		
	}

}
