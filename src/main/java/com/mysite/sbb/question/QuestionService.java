package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.category.Category;
import com.mysite.sbb.category.CategoryRepository;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final CategoryRepository categoryRepository;

	private Specification<Question> search(String kw, int ct) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				
//				q.get("category") == category
//				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), cb.like(q.get("content"), "%" + kw + "%"),
//						cb.like(u1.get("username"), "%" + kw + "%"), cb.like(a.get("content"), "%" + kw + "%"),
//						cb.like(u2.get("username"), "%" + kw + "%"));
				Predicate keywordPredicate = cb.or(cb.like(q.get("subject"), "%" + kw + "%"),
						cb.like(q.get("content"), "%" + kw + "%"), cb.like(u1.get("username"), "%" + kw + "%"),
						cb.like(a.get("content"), "%" + kw + "%"), cb.like(u2.get("username"), "%" + kw + "%"));
				if(ct!=0) {
					Category category = categoryRepository.getById(ct);
					Predicate categoryPredicate = cb.equal(q.get("category"), category);
					return cb.and(keywordPredicate, categoryPredicate);
				}else {
					return keywordPredicate;
				}
			}
		};
	}

	public Page<Question> getList(int page, String kw, int ct) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw, ct);
		return this.questionRepository.findAll(spec, pageable);
//		return this.questionRepository.findAllByKeyword(kw, pageable);
	}

	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String subject, String content, SiteUser user, int cateId) {
		Question q = new Question();
		// TO-DO: 카테고리 가져오기
		System.out.println("cateId>>>> " + cateId);
		Category category = categoryRepository.getById(cateId);
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		System.out.println("category>>>" + category);
		q.setCategory(category);
		System.out.println("checkmsg222");
		q.setAuthor(user);
		this.questionRepository.save(q);
	}

	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}

	public void delete(Question question) {
		this.questionRepository.delete(question);
	}

	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
}
