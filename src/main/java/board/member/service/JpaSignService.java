package board.member.service;

import java.util.HashMap;

import board.member.entity.MemberEntity;

public interface JpaSignService {

	HashMap<String, String> tokenReissue(String refreshToken) throws Exception;

	HashMap<String, String> signIn(MemberEntity memberEntity) throws Exception;
}
