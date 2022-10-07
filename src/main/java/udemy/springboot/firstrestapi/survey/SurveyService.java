package udemy.springboot.firstrestapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {
	
	 Logger logger = LoggerFactory.getLogger(getClass());
	
	 private static List<Survey> surveys = new ArrayList<>();
	 
	 static{
		 Question question1 = new Question("Question1",
	                "Most Popular Cloud Platform Today", Arrays.asList(
	                        "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
	        Question question2 = new Question("Question2",
	                "Fastest Growing Cloud Platform", Arrays.asList(
	                        "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
	        Question question3 = new Question("Question3",
	                "Most Popular DevOps Tool", Arrays.asList(
	                        "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
	 
	        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
	                question2, question3));
	 
	        Survey survey = new Survey("Survey1", "My Favorite Survey",
	                "Description of the Survey", questions);
	 
	        surveys.add(survey);
	 }

	public List<Survey> retrieveAllSurveys() {
		// TODO Auto-generated method stub
		return surveys;
	}

	public Survey retrieveSurveyById(String surveyid) {
		// TODO Auto-generated method stub
		   Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(surveyid)).findFirst();
		   
		   if(optionalSurvey.isEmpty())
			   return null;
		   
		   return optionalSurvey.get();
		   
	}

	public List<Question> retrieveSurveyQuestions(String surveyid) {
		// TODO Auto-generated method stub		
		   Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(surveyid)).findFirst();
		   
		   if(optionalSurvey.isEmpty())
			   return null;
		   
		   return optionalSurvey.get().getQuestions();
		
		 
	}

	public Question retrieveSurveyQuestionsById(String surveyid, String questionid) {
	 
		 List<Question> questions = retrieveSurveyQuestions(surveyid);
	     
		 if(questions == null)
		    return null;
		 
		 Predicate<? super Question> Predicate = question -> question.getId().equalsIgnoreCase(questionid);
		Optional<Question> optionalQuestion = questions.stream().filter(Predicate).findFirst();
		
		if(optionalQuestion.isEmpty())
			return null;
		
		return optionalQuestion.get();
		 
	}

	public String addQuestion(String surveyid, Question question) {
		 
		List<Question>questions = retrieveSurveyQuestions(surveyid);
		
		if(questions != null)
		{		 
			question.setId(getRandomId());
			questions.add(question);	
			
		}
		
		return question.getId();
			
	}

	private String getRandomId() {
		SecureRandom securerandom = new SecureRandom();
		String questionId = new BigInteger(32, securerandom).toString();
		return questionId;
	}

	public String deleteSurveyQuestionsById(String surveyid, String questionid) {
		// TODO Auto-generated method stub
		List<Question>surveyQuestions = retrieveSurveyQuestions(surveyid);
	    
	 
		if(surveyQuestions == null)
			return null;
		
 	    // logger.debug("The logger here is " + surveyQuestions.toString());  
		
	

		 		
		Predicate<? super Question> Predicate = question -> question.getId().equalsIgnoreCase(questionid) ;
		boolean removeIf = surveyQuestions.removeIf(Predicate);
		
		if(removeIf == true)
		   return questionid;
		
		return null;
	}

	public String updateSurveyQuestionsById(String surveyid, String questionid, Question question) {
		List<Question>survey  = retrieveSurveyQuestions(surveyid);
		Predicate<? super Question> Predicate = quest  -> quest.getId().equalsIgnoreCase(questionid) ;
		
		survey.removeIf(Predicate);
		
		survey.add(question);
		return questionid;
	}
	 
	
	
}
