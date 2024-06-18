package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "해당 메일은 이미 사용 중입니다. 아이디 찾기를 통해 확인해주세요.")
public class EmailAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public EmailAlreadyExists(String message) {
		super(message);
	}
}
