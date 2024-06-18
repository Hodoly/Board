package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm2 {
	private String username;
	
	private String password;
	
	@NotEmpty(message="이메일은 필수 항목입니다.")
	@Email
	private String email;
	
//	private String auth;
}
