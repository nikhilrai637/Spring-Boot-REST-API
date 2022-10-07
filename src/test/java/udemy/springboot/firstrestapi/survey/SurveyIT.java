package udemy.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

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
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyIT {

	@Autowired
	TestRestTemplate template;
	
	private String SPECIFIC_SURVEY_URL = "/surveys/survey1/questions";

	private String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question2";

//	{"id":"Question2","description":"Fastest Growing Cloud Platform",
//	"correctAnswer":"Google Cloud","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}

//	{"id":"Question2","description":"Fastest Growing Cloud Platform","correctAnswer":"Google Cloud","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}

	@Test
	void retrieveSurveyQuestionsById_basicScenario() throws JSONException {
		String expectedresult = """
							{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",

				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				]
				}
							""";

		
	    HttpHeaders httpHeader = createHttpContentTypeAndAuthorizationHeaders();		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null,httpHeader);
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_QUESTION_URL,HttpMethod.GET,httpEntity, String.class);
 
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		JSONAssert.assertEquals(expectedresult, responseEntity.getBody(), false);

	}
	
	@Test
	void retrieveSurveyQuestions_basicScenario() throws JSONException {
		String expectedResult = 
				""" 
				 [{"id":"Question1"}
				 ,{"id":"Question2"}
				 ,{"id":"Question3"}]
				""";
		
	    HttpHeaders httpHeader = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null,httpHeader);
		
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_SURVEY_URL,HttpMethod.GET,httpEntity, String.class);
		//ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_SURVEY_URL, String.class);
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));		
		JSONAssert.assertEquals(expectedResult,responseEntity.getBody(),false);
		
	}
	 
	//body 
	//headers Content-Type = "application/json"
	//Add headers and body in the Request template.exchange()
	//Final post request in template.exchange
	
	//Basic cmFuZ2E6ZHVtbXlkdW1teQ==
	@Test 
	void addQuestion_basicScenario() {
		String requestBody = 
				"""
					{						 
						"description": "Cloud platforms to learn in 2022",
						"correctAnswer": "AWS",
						"options":[
						"AWS",
						"Azure",
						"Google Cloud",
						"Oracle Cloud"
						]
					}				
			  """;
		
		HttpHeaders httpHeader = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody,httpHeader);
		
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_SURVEY_URL,HttpMethod.POST,httpEntity, String.class);
		
	   
	    System.out.println(responseEntity.getStatusCode());
	    System.out.println(responseEntity.getHeaders());
		
	    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
	    String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains(SPECIFIC_SURVEY_URL));
		
		ResponseEntity<String> deleteEntity = template.exchange(locationHeader,HttpMethod.DELETE,httpEntity, String.class);
		 assertTrue(deleteEntity.getStatusCode().is2xxSuccessful()); 
	    
	   
	}

	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("Content-Type", "application/json");
		httpHeader.add("Authorization", "Basic "+ performBasicAuthEncoding("admin", "password"));
		return httpHeader;
	}
	
	public String performBasicAuthEncoding(String user , String password) {
	   String combined = user + ':' + password ;
	   byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
	   		
		return new String(encodedBytes);
		
	}
	
	
	//201 CREATED
	//[Location:"http://localhost:57156/surveys/survey1/questions/3669660273", Content-Length:"0", Date:"Mon, 12 Sep 2022 15:29:25 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]

}