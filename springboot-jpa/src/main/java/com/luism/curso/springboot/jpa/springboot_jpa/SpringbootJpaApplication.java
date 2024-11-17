package com.luism.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luism.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.luism.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		findOne();
	}

	public void findOne() {
		// Person p = null;
		// Optional<Person> personOptional =repository.findById(6L);
		// if (personOptional.isPresent()){
		// p = personOptional.get();
		// }
		// System.out.println(p);

		repository.findById(1L).ifPresent(System.out::println);
	}

	public void list() {
		// List<Person> persons = (List<Person>)
		// repository.findByProgrammingLanguage("Java");
		// List<Person> persons = (List<Person>)
		// repository.buscarByProgrammingLanguage("Java", "Andres");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsValues = repository.obtenerPersonData("Java", "Andres");
		personsValues.stream().forEach(person -> System.out.println(person[0] + " es experto en " + person[1]));
	}

}
