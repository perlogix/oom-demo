package com.yeticloud.oomdemo.controller;

import com.yeticloud.oomdemo.model.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class Controller {
    private final Map<String, Data> userData = new ConcurrentHashMap<>();
    Boolean oom = false;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String friendlyTime = dtf.format(now);

    @GetMapping("/api/oom")
    public ResponseEntity<String> apiOOM() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("body", "ok");
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            var newData = new Data(generatedString);
            userData.put(newData.getId(), newData);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("ok");
        }catch (OutOfMemoryError e){
            oom = true;
            map.replace("body", "out_of_memory");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Out of Memory");
        }


    }

    @GetMapping("/users/{id}")
    public ResponseEntity<HashMap<String, Object>> getUser(@PathVariable("id") String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("body", "");
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    @GetMapping("/api")
    public ResponseEntity<HashMap<String, Object>> api() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("start_date", friendlyTime);
        map.put("out_of_memory", oom);
        if (oom) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(map);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(map);
        }
    }


}
