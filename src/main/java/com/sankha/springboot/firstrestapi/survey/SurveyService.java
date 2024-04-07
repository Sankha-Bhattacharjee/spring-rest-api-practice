package com.sankha.springboot.firstrestapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

	static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public Survey returnSurveyById(String id) {
//		for(Survey s: surveys) {
//			if(s.getId().equals(id)) {
//				return s;
//			}
//		}

		Optional<Survey> optionalSurvey = surveys.stream().filter(s -> s.getId().equals(id)).findFirst();
		if (optionalSurvey.isEmpty())
			return null;

		return optionalSurvey.get();
	}

	public List<Question> getAllSurveyQuestions(String surveyId) {
		Survey survey = returnSurveyById(surveyId);

		if (survey == null)
			return null;

		List<Question> questions = survey.getQuestions();
		return questions == null || questions.isEmpty() ? null : questions;
	}

	public Question getSurveyQuestion(String surveyId, String questionId) {

		List<Question> allSurveyQuestions = getAllSurveyQuestions(surveyId);

		if (allSurveyQuestions == null)
			return null;

		Optional<Question> optionalQuestion = allSurveyQuestions.stream().filter(q -> q.getId().equals(questionId))
				.findFirst();
		return optionalQuestion.isEmpty() ? null : optionalQuestion.get();
	}

	public Question addNewQuestionToSurvey(String surveyId, Question question) {
		List<Question> allSurveyQuestions = getAllSurveyQuestions(surveyId);
		question.setId(getRandomId());
		allSurveyQuestions.add(question);
		return question;
	}

	private String getRandomId() {
		SecureRandom random = new SecureRandom();
		String randomId = new BigInteger(32, random).toString();
		return randomId;
	}

	public String deleteSurveyQuestion(String surveyId, String questionId) {
		List<Question> allSurveyQuestions = getAllSurveyQuestions(surveyId);

		if (allSurveyQuestions == null)
			return null;
		boolean removed = allSurveyQuestions.removeIf(q -> q.getId().equals(questionId));
		return removed ? questionId : null;
	}

	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {

		List<Question> allSurveyQuestions = getAllSurveyQuestions(surveyId);
		allSurveyQuestions.removeIf(q-> q.getId().equals(questionId));
		allSurveyQuestions.add(question);
	}

}
