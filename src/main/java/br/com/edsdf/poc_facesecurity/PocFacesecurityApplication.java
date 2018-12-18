package br.com.edsdf.poc_facesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class PocFacesecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocFacesecurityApplication.class, args);
	}

	@RequestMapping("/json")
	String home() {
		return "ola";
	}
}

