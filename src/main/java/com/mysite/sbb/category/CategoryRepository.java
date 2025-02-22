package com.mysite.sbb.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
