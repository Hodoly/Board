package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "해당 메일로 가입한 사용자가 없습니다.")
public class EmailNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public EmailNotFoundException(String message) {
		super(message);
	}
}
