package pl.filmveeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmveebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmveebApplication.class, args);
		System.out.println("Działam!");
	}

}
