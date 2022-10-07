package udemy.springboot.firstrestapi.survey;

import java.util.List;

public class Question {

	public Question(String id, String description, List<String> options, String correctAnswer) {
		super();
		this.id = id;
		this.description = description;
		this.correctAnswer = correctAnswer;
		this.options = options;
	}
	
	private String id; 
	private String description;
	private String correctAnswer;
	private List<String>options;
	
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public List<String> getOptions() {
		return options;
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + ", options="
				+ options + "]";
	}
	public void setId(String questionId) {
		this.id = questionId;
		
	}
	
}
