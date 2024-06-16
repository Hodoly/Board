package com.mysite.sbb.answer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import com.mysite.sbb.question.Question;

public interface AnswerRepository extends JpaRepository<Answer,Integer>{
	Page<Answer> findByQuestion(Question question, Pageable pageable);
}
