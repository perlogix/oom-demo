package com.yeticloud.oomdemo.controller;

import com.yeticloud.oomdemo.model.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class Controller {
    private final Map<String, Data> userData = new ConcurrentHashMap<>();

    @PostMapping("/users")
    public String addUser(@RequestBody String name) {
        var newData = new Data(name);
        userData.put(newData.getId(), newData);
        return newData.getId();
    }

    @GetMapping("/users/{id}")
    public Data getUser(@PathVariable("id") String id) {
        return userData.get(id);
    }
}
