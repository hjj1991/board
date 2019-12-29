package board.member.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.api.advice.exception.CUserNotFoundException;
import board.api.service.ResponseService;
import board.configuration.security.JwtTokenProvider;
import board.member.dto.MemberDto;
import board.member.entity.MemberEntity;
import board.member.repository.JpaMemberRepository;
import board.model.response.CommonResult;
import board.model.response.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1. Sign" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

	private final JpaMemberRepository jpaMemberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;

	@ApiOperation(value = "로그인", notes = "아이디로그인을 한다.")
	@GetMapping(value = "/signin")
	public SingleResult<String> signin(@ApiParam(value = "회원ID ", required = true) @RequestParam String userId,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password) {

		MemberEntity user = jpaMemberRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
		System.out.println(user);
		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new CUserNotFoundException();

		return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
	}

	@ApiOperation(value = "가입", notes = "회원가입을 한다.")
	@PutMapping(value = "/signup")
	public CommonResult signin(@RequestBody @Valid MemberDto memberDto, BindingResult result) {

		if(result.hasErrors()) {
			
			List<FieldError> errors = result.getFieldErrors();
			HashMap<String, String> errorList = new HashMap<String, String>();
			 for (FieldError error : errors ) {
				 errorList.put(error.getField(), error.getDefaultMessage());
			        //System.out.println (error.getField() + " - " + error.getDefaultMessage());
			 }
			 
			 
			return responseService.getSingleFailResult(errorList); 
		}
			
		jpaMemberRepository.save(MemberEntity.builder().userId(memberDto.getUserId()).password(passwordEncoder.encode(memberDto.getPassword())).name(memberDto.getName()).nickName(memberDto.getNickName()).emailAddr(memberDto.getEmailAddr())
				.roles(Collections.singletonList("ROLE_USER")).build());
		return responseService.getSuccessResult();
	}
}