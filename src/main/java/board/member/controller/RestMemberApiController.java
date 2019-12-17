package board.member.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
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
	public ResponseEntity<?> signUp(@RequestBody @Valid MemberDto memberDto, BindingResult result) throws Exception{
		
		if(result.hasErrors()) {
			
			List<FieldError> errors = result.getFieldErrors();
			HashMap<String, String> errorList = new HashMap<String, String>();
			 for (FieldError error : errors ) {
				 errorList.put(error.getField(), error.getDefaultMessage());
			        //System.out.println (error.getField() + " - " + error.getDefaultMessage());
			 }
			 
			 
			return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST); 
		}
		
		memberService.signUp(memberDto);
		return new ResponseEntity<>(memberDto, HttpStatus.OK);
		
	}

}
