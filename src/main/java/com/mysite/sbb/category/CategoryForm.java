package com.mysite.sbb.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryForm {
	@NotEmpty(message="카테고리명은 필수입니다.")
	private String name;
}
