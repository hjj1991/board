package board.member.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import board.configuration.security.JwtTokenProvider;
import board.member.entity.MemberEntity;
import board.member.entity.TokenEntity;
import board.member.repository.JpaMemberRepository;
import board.member.repository.JpaTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JpaSignServiceImpl implements JpaSignService {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	JpaMemberRepository jpaMemberRepository;
	@Autowired
	JpaTokenRepository jpaTokenRepository;
	
	@Value("spring.jwt.secret")
	private String secretKey;

	@Override
	public HashMap<String, String> signIn(MemberEntity memberEntity) throws Exception {
		
		HashMap<String, String> result = new HashMap<>();
		List<String> tokenInfo = new ArrayList<String>();
		tokenInfo = jwtTokenProvider.createToken(memberEntity.getUsername(), memberEntity.getRoles());
		result.put("X_AUTH_TOKEN", tokenInfo.get(0));
		result.put("exAuthToken", tokenInfo.get(1));
		result.put("X_REFRESH_TOKEN", jwtTokenProvider.createRefreshToken(memberEntity.getUsername(), memberEntity.getRoles()));
		result.put("name", memberEntity.getName());
		result.put("nickName", memberEntity.getNickName());
		result.put("emailAddr", memberEntity.getEmailAddr());
		
		memberEntity.setLoginDate(LocalDateTime.now());
		jpaMemberRepository.save(memberEntity);
		
		
		return result;
	}
	@Override
	public HashMap<String, String> signOut(String refreshToken) throws Exception {
		String jwtUserId = null;
		HashMap<String, String> result = new HashMap<String, String>();
		if(jwtTokenProvider.validateRefreshToken(refreshToken)) {	//리프레쉬 토큰 검증 후 토큰 디코딩하여 정보가져옴
			jwtUserId = Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(refreshToken).getBody().getSubject();
			jpaTokenRepository.deleteById(jwtUserId);
			result.put("success",  "true");
		}else {
			result.put("code", "999");
		}
		
		return result;
		
//		result.put("X-AUTH-TOKEN", jwtTokenProvider.createToken(jwtUserId,jwtRoles)); //가져온 정보로 토큰 재생성
		
	}

	@Override
	public HashMap<String, String> tokenReissue(String refreshToken) throws Exception {
		List<String> jwtRoles =  new ArrayList<String>();
		String jwtUserId = null;
		HashMap<String, String> result = new HashMap<String, String>();
		List<String> tokenInfo = new ArrayList<String>();
		if(jwtTokenProvider.validateRefreshToken(refreshToken)) {	//리프레쉬 토큰 검증 후 토큰 디코딩하여 정보가져옴
			TokenEntity tokenEntity = jpaTokenRepository.findByRefreshToken(refreshToken);
			jwtRoles.add(Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(refreshToken).getBody().get("roles").toString().replace("[", "").replace("]", ""));	//토큰 해석하여 유저 권한 넣어줌
			jwtUserId = Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(refreshToken).getBody().getSubject();
			tokenInfo = jwtTokenProvider.createToken(jwtUserId,jwtRoles);
			result.put("code", "1");
			result.put("X_AUTH_TOKEN", tokenInfo.get(0));
			result.put("exAuthToken", tokenInfo.get(1));
		}else {
			result.put("code", "999");
		}
		
		return result;
		
//		result.put("X-AUTH-TOKEN", jwtTokenProvider.createToken(jwtUserId,jwtRoles)); //가져온 정보로 토큰 재생성
		
	}
}
