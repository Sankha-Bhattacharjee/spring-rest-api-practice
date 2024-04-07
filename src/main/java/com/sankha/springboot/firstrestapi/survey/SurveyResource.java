package com.sankha.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		
		// Create new question=> POST: /surveys/Survey1/questions/
		@PostMapping("/surveys/{surveyId}/questions")
		public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){
			
			Question newQuestion = service.addNewQuestionToSurvey(surveyId, question);
			String newQuestionId = newQuestion.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newQuestionId}").buildAndExpand(newQuestionId).toUri();
			
			//return ResponseEntity.created(location).body(newQuestion); // returns 201 status with the question body
			return ResponseEntity.created(location).build(); // return only response status, nothing in the body
		}
		
		// Delete Question=> DELETE: /surveys/Survey1/questions/Question1
		@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
		public ResponseEntity<Object> deleteSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId){
			
			String questionIdofRemoved = service.deleteSurveyQuestion(surveyId, questionId);
			return ResponseEntity.noContent().build();
		}
		
		// Update Question=> PUT: /surveys/Survey1/questions/Question1
		@PutMapping("/surveys/{surveyId}/questions/{questionId}")
		public ResponseEntity<Object> updateSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question question){
			
			service.updateSurveyQuestion(surveyId, questionId, question);
			//return ResponseEntity.noContent().build();
			return ResponseEntity.ok(question);
		}

}
