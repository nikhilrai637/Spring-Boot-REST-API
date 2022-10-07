package udemy.springboot.firstrestapi.helloWorld;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {
	
	@RequestMapping("hello-world")
	public String HelloWolrd()
	{
		return "Hello World!";
	}
	
	@RequestMapping("hello-world-bean")
	public HelloWorldBean HelloWorldBean()
	{
		return new HelloWorldBean("Hello World Bean");
	}

	@RequestMapping("hello-world-path-param/name/{name}/message/{message}")
	public HelloWorldBean HelloWorldBean(@PathVariable String   name,@PathVariable String message)
	{
		return new HelloWorldBean("Hello World ,"+name+","+message);
	}
}
