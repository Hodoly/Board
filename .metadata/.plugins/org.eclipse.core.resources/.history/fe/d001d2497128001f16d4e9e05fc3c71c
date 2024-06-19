package com.mysite.sbb.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Page<Comment> findByQuestion(Question question, Pageable pageable);

	ArrayList<Comment> findByQuestion(Question question);
	ArrayList<Comment> findByAnswer(Answer answer);
}
