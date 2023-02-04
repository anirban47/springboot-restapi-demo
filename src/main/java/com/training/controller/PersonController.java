package com.training.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Person;
import com.training.repository.PersonRepo;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
	@Autowired
	PersonRepo personRepo;

	@GetMapping
	public List<Person> findAllPersons() {
		return (List<Person>) personRepo.findAll();
	}

	@PostMapping
	public Person addPerson(@RequestBody Person person) {
		return personRepo.save(person);
	}

	@GetMapping("/{pid}")
	public ResponseEntity<Person> findPerson(@PathVariable("pid") int pid) {
	    Optional<Person> person = personRepo.findById(pid);
	    
	    if(person.isPresent()) {
	        return ResponseEntity.ok().body(person.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
