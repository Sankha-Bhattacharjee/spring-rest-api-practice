package com.sankha.springboot.firstrestapi.users;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDtailsCommandLineRunner implements CommandLineRunner {
	@Autowired
	private UserDetailsRepository repository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String... args) throws Exception {
		repository.save(new UserDetails("Sankha", "Admin"));
		repository.save(new UserDetails("Suvro", "Admin"));
		repository.save(new UserDetails("Hog Rider", "User"));
		
		//List<UserDetails> users = repository.findAll();
		List<UserDetails> users = repository.findByRole("Admin");
		users.forEach(u -> logger.info(u.toString()));
	}

}
