package board.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.api.advice.exception.CUserNotFoundException;
import board.api.service.ResponseService;
import board.configuration.security.JwtTokenProvider;
import board.member.entity.MemberEntity;
import board.member.repository.JpaMemberRepository;
import board.model.response.CommonResult;
import board.model.response.ListResult;
import board.model.response.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1. Member" })
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/v1")
public class MemberController {
	private final JpaMemberRepository jpaMemberRepo;
	private final ResponseService responseService; // 결과를 처리할 Service
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@GetMapping(value = "/users")
	public ListResult<MemberEntity> findAllUser() {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
		return responseService.getListResult(jpaMemberRepo.findAll());
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public SingleResult<MemberEntity> findUserById(@RequestHeader("X_AUTH_TOKEN") String authToken) {
		// 결과데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
		return responseService.getSingleResult(jpaMemberRepo.findByUserId(jwtTokenProvider.getUserPk(authToken)).orElseThrow(CUserNotFoundException::new));
	}

	@ApiOperation(value = "회원 입력", notes = "회원을 입력한다")
	@PostMapping(value = "/user")
	public SingleResult<MemberEntity> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name,
			@ApiParam(value = "이메일주소", required = true) @RequestParam String emailAddr) {
		MemberEntity user = MemberEntity.builder().userId(uid).name(name).emailAddr(emailAddr).build();
		return responseService.getSingleResult(jpaMemberRepo.save(user));
	}
	
	@ApiOperation(value = "중복아이디 체크", notes = "아이디 입력")
	@GetMapping(value = "/user/checkid/{userId}")
	public SingleResult<Boolean> checkId(
			@ApiParam(value = "회원ID", required = true) @PathVariable String userId){
		
		return responseService.getSingleResult(jpaMemberRepo.existsByUserId(userId));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
	@PutMapping(value = "/user")
	public SingleResult<MemberEntity> modify(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
			@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name,
			@ApiParam(value = "이메일주소", required = true) @RequestParam String emailAddr) {
		MemberEntity user = MemberEntity.builder().msrl(msrl).userId(uid).name(name).emailAddr(emailAddr).build();
		return responseService.getSingleResult(jpaMemberRepo.save(user));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X_AUTH_TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
	@DeleteMapping(value = "/user/{msrl}")
	public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
		jpaMemberRepo.deleteById(msrl);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responseService.getSuccessResult();
	}

}