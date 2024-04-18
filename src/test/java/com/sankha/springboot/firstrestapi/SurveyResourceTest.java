package com.sankha.springboot.firstrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.SystemPropertyUtils;

import com.sankha.springboot.firstrestapi.survey.Question;
import com.sankha.springboot.firstrestapi.survey.SurveyResource;
import com.sankha.springboot.firstrestapi.survey.SurveyService;

@WebMvcTest(controllers = SurveyResource.class)
class SurveyResourceTest {

	@MockBean
	private SurveyService surveyService;
	@MockBean
	private Question mockQuestion;
	@Autowired
	private MockMvc mockMvc;
	
	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions";
	
	@Test
	void getSpecificSurveyQuestion_404Scenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(mvcResult.getResponse());
		System.out.println(mvcResult.getResponse().getStatus()); //404
		
		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void getSpecificSurveyQuestion_basicScenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		when(surveyService.getSurveyQuestion("Survey1", "Question1")).thenReturn(question);
		
		String expectedResponse= """
				{
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"
				}
				""";
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		System.out.println(mvcResult.getResponse().getStatus()); //200
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
	}
	@Test
	public void addNewSurveyQuestion_basicScenario() throws Exception {
		String requestBody = """
				{
				    "description": "Your favourite Language",
				    "options": [
				        "Java",
				        "Python",
				        "Javascript",
				        "Rust"
				    ],
				    "correctAnswer": "Java"
				}
						""";
		
		when(surveyService.addNewQuestionToSurvey(anyString(), any())).thenReturn(mockQuestion);
		when(mockQuestion.getId()).thenReturn("SOME_ID");
		
		RequestBuilder requestBuilder =   
				MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		String location = response.getHeaders("location").get(0);
		System.out.println(location);
		
		assertEquals(201,response.getStatus()); //201
		assertTrue(location.contains("surveys/Survey1/questions/SOME_ID"));
	}

}
