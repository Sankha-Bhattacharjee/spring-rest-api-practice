package com.sankha.springboot.firstrestapi.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller // Used when you want to map to a view, Requires @ResponseBody to return object as is, otherwise it looks for view
@RestController //Doesn't require @ResponseBody as it returns object as is
public class HelloWorldResource {
	// /hello-world => "Hello World"
	@RequestMapping(path = "/hello-world", method = RequestMethod.GET)
	//@ResponseBody // For returning the string as response bpdy, otherwise spring mvc will look for a resource name "Hello World"
	public String sayHellowWorld() {
		return "Hello World";
	}
	
	@RequestMapping(path = "/hello-world-bean", method = RequestMethod.GET)
	public HelloWorldBean hellowWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	// Path variables or Path params
	// /users/Sankha/todos/1
	
	@RequestMapping(path = "/hello-world-path-params/{name}", method = RequestMethod.GET)
	public HelloWorldBean helloWorldPathParams(@PathVariable String name) {
		return new HelloWorldBean("Hello World, "+name);
	}
	
	@RequestMapping(path = "/hello-world-path-params/{name}/message/{m}", method = RequestMethod.GET)
	public HelloWorldBean helloWorldMultiPathParams(@PathVariable String name, @PathVariable("m") String message) {
		return new HelloWorldBean("Hello World "+name+","+message);
	}

}
