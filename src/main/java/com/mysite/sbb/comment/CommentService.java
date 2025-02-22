package com.mysite.sbb.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	
	public Comment create(Question question, Answer answer, String content, SiteUser author, String kind) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateDate(LocalDateTime.now());
		comment.setAuthor(author);
		comment.setKind(kind);
		if(question != null) {
			comment.setQuestion(question);
			this.commentRepository.save(comment);
		}else {
			comment.setAnswer(answer);
			this.commentRepository.save(comment);
		}
		return comment;
	}

//	public Page<Comment> getQuestionComment(Question question, int page) {
//		List<Sort.Order> sorts = new ArrayList<>();
//		sorts.add(Sort.Order.asc("createDate"));
//		Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
//		return this.commentRepository.findByQuestion(question, pageable);
//	}
	
	public ArrayList<Comment> getQuestionComment(Question question) {
		return this.commentRepository.findByQuestion(question);
	}
	
	
	public ArrayList<Comment> getAnswerComment(Answer answer) {
		return this.commentRepository.findByAnswer(answer);
	}

}
