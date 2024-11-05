package com.alura.lazarte.literalura;


import com.alura.lazarte.literalura.principal.Principal;
import com.alura.lazarte.literalura.repository.AutorRepository;
import com.alura.lazarte.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplicationConsola implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplicationConsola.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.app();
	}
}
