package com.example.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/person")
public class ExampleController {

    private final Map<Integer, Person> peronlist = new HashMap<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 5; i++) {
            peronlist.put(i, new Person("zhangsan" + i, i));
        }
    }

    @GetMapping("/hello")
    public HttpEntity hello() {
        ResponseEntity<String> result = ResponseEntity.ok("hello");
        return result;
    }

    @PostMapping("")
    public HttpEntity<Integer> save(@RequestBody Person p) {
        int uid = ThreadLocalRandom.current().nextInt(100, 999);
        peronlist.put(uid, p);
        return new ResponseEntity(uid, HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public HttpEntity<Person> get(@PathVariable int uid) {
        Person p = peronlist.get(uid);
        return new ResponseEntity(p, HttpStatus.OK);
    }

    @PutMapping("/{uid}")
    public HttpEntity<Boolean> upd(@PathVariable int uid, @RequestBody Person p) {
        peronlist.put(uid, p);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public HttpEntity<Boolean> del(@PathVariable int uid) {
        peronlist.remove(uid);
        return new ResponseEntity(true, HttpStatus.OK);
    }

}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
