package com.luism.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.luism.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    //@Query("select p from Person p where p.programmingLanguage = ?1 and p.name = ?2")
    //List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage = ?1 and p.name = ?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
    
    @Query("select p.name, p.programmingLanguage from Person p where p.name = ?1")
    List<Object[]> obtenerPersonData(String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.name = ?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

}
