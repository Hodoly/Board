package com.mysite.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePw {
	private String username;
	
	@NotEmpty(message="비밀번호는 필수 항목입니다.")
	private String password1;
	
	@Size(min=4, max=20)
	@NotEmpty(message="비밀번호는 필수 항목입니다.")
	private String password2;
}
