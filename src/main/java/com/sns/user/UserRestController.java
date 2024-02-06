package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;

	/**
	 * 아이디 중복확인 API
	 * 
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {

		Map<String, Object> result = new HashMap<>();
		result.put("is_duplicated_id", false);

		// select
		UserEntity userEntity = userBO.getUserEntityByLoginId(loginId);
		result.put("code", 200);

		if (userEntity != null) {
			result.put("is_duplicated_id", true);
		} 

		return result;
	}

	/**
	 * 회원가입 API
	 * 
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password, 
			@RequestParam("name") String name,
			@RequestParam("email") String email) {

		// 비밀번호 해싱
		String hashedPassword = EncryptUtils.md5(password);

		// db insert
		Integer userId = userBO.addUser(loginId, hashedPassword, name, email);

		Map<String, Object> result = new HashMap<>();
		if (userId != null) {
			// 응답
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// password hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// loginId, hashedPassword로 UserEntity => null or 채워져있음
		UserEntity userEntity = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		Map<String, Object> result = new HashMap<>();
		if (userEntity != null) {
			// 로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("userId", userEntity.getId());
			session.setAttribute("userLoginId", userEntity.getLoginId());
			session.setAttribute("userName", userEntity.getName());
			
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			// 로그인 불가
			result.put("code", 500);
			result.put("errorMessage", "존재하지 않는 사용자입니다.");
		}
		
		return result;
	}
}