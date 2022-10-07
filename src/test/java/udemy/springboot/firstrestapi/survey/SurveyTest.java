package udemy.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = SurveyResource.class)
class SurveyTest {
   
	
	@MockBean 
	private SurveyService surveyservice;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
	private static String SPECIFIC_SURVEY_URL = "/surveys/survey1/questions";
	
	
	@Test
	void retrieveSurveyQuestions_basicScenario() throws Exception {
		
		RequestBuilder requestBuilder =  MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		
		Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                        "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		
		when(surveyservice.retrieveSurveyQuestionsById("Survey1","Question1")).thenReturn(question1);
		
		MvcResult mvcResult  = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = """ 
					 {
							"id": "Question1",
							"description": "Most Popular Cloud Platform Today",
							"correctAnswer": "AWS",
							"options": [
							"AWS",
							"Azure",
							"Google Cloud",
							"Oracle Cloud"
							]
					}
								  """;
      
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		System.out.println(mvcResult.getResponse().getStatus());
		
		assertEquals(200,mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse,mvcResult.getResponse().getContentAsString(),false);
		
	}
	
	@Test
	void retrieveSurveyQuestions_404Scenario() throws Exception {
		
		RequestBuilder requestBuilder =  MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult  = mockMvc.perform(requestBuilder).andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		System.out.println(mvcResult.getResponse().getStatus());
		
		assertEquals(404,mvcResult.getResponse().getStatus());
		
	}
	
	//POST
	
	//body
	
	//URL 
	
	//Check - location header
	//Check - reponse status
	
	@Test
	void addQuestion_basicScenario() throws Exception {
		
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
		
		when(surveyservice.addQuestion( anyString() ,  any())).thenReturn("SOME_ID");
		
		RequestBuilder requestBuilder =  MockMvcRequestBuilders.post(SPECIFIC_SURVEY_URL)
											.accept(MediaType.APPLICATION_JSON)
											.content(requestBody)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult  = mockMvc.perform(requestBuilder).andReturn();
		
		
		String locationHeader = mvcResult.getResponse().getHeader("Location");
		
		System.out.println(mvcResult.getResponse().getStatus());
		
		assertEquals(201,mvcResult.getResponse().getStatus());
		assertTrue(locationHeader.contains("surveys/survey1/questions/SOME_ID"));
	 
		
	}
	

}
