package com.mysite.sbb.email;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.EmailAlreadyExists;
import com.mysite.sbb.EmailNotFoundException;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RedisController {
	private final RedisService redisUserService;
	private final UserService userService;
	private final EmailUtil emailUtil;

	@PostMapping("/email/pwfind")
	@ResponseBody
	public Map<String, Object> EmailP(@RequestBody Map<String, Object> params) {
		// 인증번호 저장
		String serial = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
		String body = (String) params.get("body") + serial;
		return emailUtil.sendEmailPw((String) params.get("username"), (String) params.get("subject"), body, serial);

	}
	
	@PostMapping("/email/idfind")
	@ResponseBody
	public Map<String, Object> EmailI(@RequestBody Map<String, Object> params) {
		// 인증번호 저장
		String serial = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
		String body = (String) params.get("body") + serial;
		int present = userService.getUserEmail((String) params.get("email"));
		if(present==0) {
			throw new EmailNotFoundException("This email not exists");
		}else {
			return emailUtil.sendEmailSign((String) params.get("email"), (String) params.get("subject"), body, serial);
		}

	}
	
	@PostMapping("/email/signup")
	@ResponseBody
	public Map<String, Object> EmailS(@RequestBody Map<String, Object> params) {
		// 인증번호 저장
		String serial = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
		String body = (String) params.get("body") + serial;
		int present = userService.getUserEmail((String) params.get("email"));
		if(present==1) {
			throw new EmailAlreadyExists("This email already exists");
		}else {
			return emailUtil.sendEmailSign((String) params.get("email"), (String) params.get("subject"), body, serial);
		}

	}

	@PostMapping("/check/pwfind")
	@ResponseBody
	public String CheckAuthPw(@RequestBody Map<String, Object> params) {
		String serial = (String) params.get("serial");
		String username = (String) params.get("username");
		String email = userService.getUser(username).getEmail();
		String getSerial = redisUserService.getUserByEmail(email);
		if (getSerial.equals(serial)) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@PostMapping("/check/idfind")
	@ResponseBody
	public String CheckAuthId(@RequestBody Map<String, Object> params) {
		String serial = (String) params.get("serial");
		String email = (String) params.get("email");
		System.out.println(email);
		String getSerial = redisUserService.getUserByEmail(email);
		if (getSerial.equals(serial)) {
			System.out.println("checkmsg111");
			SiteUser user = userService.getUserByEmail(email);
			return user.getUsername();
		} else {
			System.out.println("checkmsg222");
			return "0";
		}
	}
	
	@PostMapping("/check/signup")
	@ResponseBody
	public String CheckAuthEmail(@RequestBody Map<String, Object> params , HttpServletRequest request) {
		HttpSession session = request.getSession();
		String serial = (String) params.get("serial");
		String email = (String) params.get("email");
		String getSerial = redisUserService.getUserByEmail(email);
		if (getSerial.equals(serial)) {
			session.setAttribute("authRecord", "1");
			return "1";
		} else {
			session.setAttribute("authRecord", "0");
			return "0";
		}
	}
}
