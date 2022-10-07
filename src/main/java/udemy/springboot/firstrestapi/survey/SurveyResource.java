package udemy.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilderFactory;

@RestController
public class SurveyResource {
	

	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}
		
	private SurveyService surveyService;
	
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys()
	{
		return surveyService.retrieveAllSurveys();
	}

	@RequestMapping("/surveys/{surveyid}")
	public  Survey retrieveSurveyById(@PathVariable String surveyid)
	{
		Survey survey = surveyService.retrieveSurveyById(surveyid);
		
		if(survey == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey;
	}
	
	@RequestMapping("/surveys/{surveyid}/questions")
	public List<Question> retrieveSurveyQuestions(@PathVariable String surveyid)
	{
		List<Question> questions = surveyService.retrieveSurveyQuestions(surveyid);
		
		if(questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return questions;
	}
	
	@RequestMapping("/surveys/{surveyid}/questions/{questionid}")
	public Question retrieveSurveyQuestionsById(@PathVariable String surveyid,@PathVariable String questionid)
	{
		Question question = surveyService.retrieveSurveyQuestionsById(  surveyid ,   questionid);
		
		if(question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return question;
	}
	
	@RequestMapping(value = "/surveys/{surveyid}/questions",method = RequestMethod.POST)
	public ResponseEntity<Object> addQuestion(@PathVariable String surveyid,@RequestBody Question question )
	{
		 String questionid = surveyService.addQuestion(  surveyid ,  question);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionid}").buildAndExpand(questionid).toUri();
		return  ResponseEntity.created(location).build();
	}
	
	@RequestMapping(value = "/surveys/{surveyid}/questions/{questionid}" , method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestionsById(@PathVariable String surveyid,@PathVariable String questionid)
	{
		String deletedquestionid = surveyService.deleteSurveyQuestionsById(  surveyid ,   questionid);
		
		if(deletedquestionid == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return  ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/surveys/{surveyid}/questions/{questionid}" , method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSurveyQuestionsById(@PathVariable String surveyid,@PathVariable String questionid,@RequestBody Question question)
	{
		String updatedquestionid = surveyService.updateSurveyQuestionsById(  surveyid ,   questionid,question);
		
		if(updatedquestionid == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return  ResponseEntity.noContent().build();
	}
	
}
