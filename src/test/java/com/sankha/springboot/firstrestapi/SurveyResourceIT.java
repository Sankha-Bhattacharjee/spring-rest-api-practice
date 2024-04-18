package com.sankha.springboot.firstrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";

	@Autowired
	private TestRestTemplate template;

	@Test
	public void testGetSpecificSurveyQuestionBasicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		// System.out.println(responseEntity.getBody());
		// //{"id":"Question1","description":"Most Popular Cloud Platform
		// Today","options":["AWS","Azure","Google Cloud","Oracle
		// Cloud"],"correctAnswer":"AWS"}
		// System.out.println(responseEntity.getHeaders());//[Content-Type:"application/json",
		// Transfer-Encoding:"chunked", Date:"Sun, 14 Apr 2024 14:33:27 GMT",
		// Keep-Alive:"timeout=60", Connection:"keep-alive"]
		// System.out.println(responseEntity.getStatusCode());//200 OK
		// System.out.println(responseEntity.getStatusCodeValue());//200

		String expectedResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","correctAnswer":"AWS"}
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().getContentType().toString());
		assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		// assertEquals(expectedResponse.trim(), responseEntity.getBody());
	}

	@Test
	public void testGetSurveyQuestionsBasicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

		String expectedResponse = """
				[
						{
						"id": "Question1"
						},
						{
						"id": "Question2"
						},
						{
						"id": "Question3"
						}
						]
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().getContentType().toString());
		assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}

	@Test
	public void testAddNewSurveyQuestion() {
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
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity httpEntity = new HttpEntity<String>(requestBody, headers);
		
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getHeaders());
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));
		
		template.delete(locationHeader);

	}

}
