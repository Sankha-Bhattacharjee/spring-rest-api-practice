package com.sankha.springboot.firstrestapi.survey;

import java.util.List;

public class Survey {

	private String id;
	private String description;
	private String title;
	private List<Question> questions;

	public Survey() {
	}

	public Survey(String id, String description, String title, List<Question> questions) {
		super();
		this.id = id;
		this.description = description;
		this.title = title;
		this.questions = questions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", description=" + description + ", title=" + title + ", questions=" + questions
				+ "]";
	}

}
