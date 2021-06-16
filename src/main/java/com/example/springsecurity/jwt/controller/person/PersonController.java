package com.example.springsecurity.jwt.controller.person;

import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
    private final List<Person> people = Stream.of(
            new Person("P1", 1),
            new Person("P2", 2),
            new Person("p3", 3)
    ).collect(Collectors.toList());

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<Person> all() {
        return people;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public List<Person> create() {
        people.add(new Person(String.valueOf(new Random().nextDouble()), new Random().nextInt()));
        return people;
    }

    @Value
    public static class Person {
        String name;
        int age;
    }
}
