package udemy.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class UserDetailsCommandLineRunner implements CommandLineRunner{

	 Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsRepository repository;
	 
	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
	  repository.save(new UserDetails("Nikhil","Admin"));
	  repository.save(new UserDetails("Daksh","Admin"));
	  repository.save(new UserDetails("Reena","User"));
	  repository.save(new UserDetails("Sarvesh","User"));
	  
	  List<UserDetails> users = repository.findByRole("User");
 	  
      users.forEach(user -> logger.info(user.toString()));
		
	}

	
}
