package com.sankha.springboot.firstrestapi.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SurveyResource {
	@Autowired
	private SurveyService service;
	
	// /surveys
	@GetMapping("/surveys")
	public List<Survey> getAllSurveys(){
		return service.getSurveys();
	}
	
	// /surveys/Survey1
	@GetMapping("/surveys/{surveyId}")
	public Survey getSurveyByServeyId(@PathVariable("surveyId") String surveyId) {
		Survey survey = service.returnSurveyById(surveyId);
		
		if(survey == null) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey;
	}
	
	// /surveys/Survey1/questions
	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> getSurveyQuestions(@PathVariable String surveyId){
		
		List<Question> surveyQuestions = service.getAllSurveyQuestions(surveyId);
		
		if(surveyQuestions==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return surveyQuestions;
	}
	
	// /surveys/Survey1/questions/Question1
		@GetMapping("/surveys/{surveyId}/questions/{questionId}")
		public Question getSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId){
			
			Question surveyQuestions = service.getSurveyQuestion(surveyId, questionId);
			
			if(surveyQuestions==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			return surveyQuestions;
		}

}
