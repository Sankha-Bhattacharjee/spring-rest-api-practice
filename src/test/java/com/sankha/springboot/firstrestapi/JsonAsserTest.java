package com.sankha.springboot.firstrestapi;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class JsonAsserTest {
	String expectedResponse = """
			{"id":"Question1","description":"Most Popular Cloud Platform Today","correctAnswer":"AWS"}
			""";
	String actualResponse = """
			{
				"id":"Question1",
			"description":"Most Popular Cloud Platform Today",
			"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
			"correctAnswer":"AWS"}
			""";
	

	@Test
	void jsonAssert_basics() throws JSONException {
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
