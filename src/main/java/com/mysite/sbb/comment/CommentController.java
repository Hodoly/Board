package com.mysite.sbb.comment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final CommentService commentService;
	private final UserService userService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/q/{id}")
	public String commentCreateQuestion(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("commentForm", new Comment());
			model.addAttribute("question", question);
			return "question_detail";
		}
		this.commentService.create(question, null, commentForm.getContent(), siteUser, "1");
		return String.format("redirect:/question/detail/%s", question.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/a/{id}")
	public String commentCreateAnswer(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		Question question = answerService.getAnswer(id).getQuestion();
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("commentForm", new Comment());
			model.addAttribute("answer", answer);
			return "question_detail";
		}
		this.commentService.create(null, answer, commentForm.getContent(), siteUser, "2");
		return String.format("redirect:/question/detail/%s", question.getId());
	}

	@GetMapping("/list/{kind}/{id}/{page}")
	@ResponseBody
	public String getQuestionComment(@PathVariable("kind") String kind,@PathVariable("id") Integer id, @PathVariable("page") Integer page) {
		System.out.println("checkmsg11111");
		ArrayList<Comment> comment = null;
		if(kind.equals("q")) {
			Question question = this.questionService.getQuestion(id);
			comment = this.commentService.getQuestionComment(question);
		}else {
			Answer answer = this.answerService.getAnswer(id);
			comment = this.commentService.getAnswerComment(answer);
		}
		
		ArrayList<CommentDTO> dto = new ArrayList<CommentDTO>();
		int start = page * 3;
		int end;
		if(comment.size()<start+3) {
			end = comment.size()-1;
		}else {
			end = page+2;
		}
		
		for(int i=start; i<=end; i++) {
			var j=0;
			try {
				dto.add(j,CommentDTO.toDTO(comment.get(i)));
				j = j + 1;
			}catch(DataNotFoundException e){
				e.printStackTrace();
			}
			
		}
		Gson gson = new Gson();
		return gson.toJson(dto);
	}
	
}
