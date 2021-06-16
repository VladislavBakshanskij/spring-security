package com.example.springsecurity.basic.controller;

import com.example.springsecurity.basic.model.Person;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final List<Person> people = Stream.of(
            new Person(1, "Vlad", 20),
            new Person(2, "Oleg", 22),
            new Person(3, "Lada", 23)
    ).collect(Collectors.toList());

    @GetMapping
    public List<Person> get() {
        return people;
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        people.add(person);
        return person;
    }

    @GetMapping("{id}")
    public Person get(@PathVariable int id) {
        return people.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("{id}")
    public Person delete(@PathVariable int id) {
        var p = people.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        people.remove(p);
        return p;
    }
}
