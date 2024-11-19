package com.luism.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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
		// create();
		// findOne();
		// update();
		// delete2();
		personalizedQueries();
	}

	public void findOne() {
		// Person p = null;
		// Optional<Person> personOptional =repository.findOneName("Pepe");
		// if (!personOptional.isPresent()) {
		// p = personOptional.get();
		// }
		// System.out.println(p);

		repository.findByNameContaining("hn").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner sc = new Scanner(System.in);
		System.out.println("============ Consulta solo el nombre por el id ============");
		System.out.println("Ingrese el id para el nombre:");
		Long id = sc.nextLong();
		sc.close();

		System.out.println("============ Mostrando solo el nombre ============");
		String name = repository.getNameById(id);
		System.out.println(name);

		System.out.println("============ Mostrando solo el id ============");
		Long idDb = repository.getIdById(id);
		System.out.println(idDb);

		System.out.println("============ Mostrando el nombre completo y el ID");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("============ Consulta por campos personalizados por el ID ============");
		Optional<Object> optionalReg = repository.obtenerPersonDataById(id);

		if (optionalReg.isPresent()) {
			Object[] personReg = (Object[]) optionalReg.orElseThrow();
			System.out.println("Id: "+personReg[0]+", Nombre: "+personReg[1]+", Apellido: "+personReg[2]+", Lenguaje: "+personReg[3]);

		}

		System.out.println("============ Consulta por campos personalizados lista ============");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out
				.println("Id: " + reg[0] + ", Nombre: " + reg[1] + ", Apellido: " + reg[2] + ", Lenguaje: " + reg[3]));

	}

	@Transactional
	public void update() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el ID de la persona:");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		// optionalPerson.ifPresent(person -> {

		if (optionalPerson.isPresent()) {
			Person personDb = optionalPerson.orElseThrow();
			System.out.println(personDb);
			System.out.println("Introduce el lenguaje de programación:");
			String programmingLanguage = sc.next();
			personDb.setProgrammingLanguage(programmingLanguage);
			Person personUpdate = repository.save(personDb);
			System.out.println(personUpdate);
		} else {
			System.out.println("El usuario no existe!");
		}

		// });

		sc.close();
	}

	@Transactional
	public void delete() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el ID de la persona:");
		Long id = sc.nextLong();

		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);

		sc.close();

	}

	@Transactional
	public void delete2() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el ID de la persona:");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(repository::delete,
				() -> System.out.println("No existe la persona con el id: " + id));

		repository.findAll().forEach(System.out::println);

		sc.close();

	}

	@Transactional
	public void create() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca el nombre de la persona");
		String name = sc.next();
		System.out.println("Introduzca el apellido de la persona");
		String lastName = sc.next();
		System.out.println("Introduzca el lenguaje de programaciónPedr");
		String programmingLanguage = sc.next();
		sc.close();

		Person p = new Person(null, name, lastName, programmingLanguage);
		Person personNew = repository.save(p);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);

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
