package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.service.JwtUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HelloWorldController {

	private Log log = LogFactory.getLog(ResponsavelController.class);

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		var user = userDetailsService.getUserFromContext();
		return "Hello World";
	}

}