package saad.projet.jo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoApplication.class, args);
	}

	@RequestMapping("/")
	public String home(){
		return "projet olympe";
	}
}
