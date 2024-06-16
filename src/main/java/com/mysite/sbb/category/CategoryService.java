package com.mysite.sbb.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public Category create(String name) {
		Category category = new Category();
		category.setName(name);
		this.categoryRepository.save(category);
		return category;
	}

	public List getCategory() {
		List<Category> category = categoryRepository.findAll();
		return category;
	}
}
