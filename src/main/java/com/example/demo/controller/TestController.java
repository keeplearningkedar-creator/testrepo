package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class TestController {

    @Autowired
    private UserService userService;


    @PostMapping(path = "/create/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Creating user: " + user.toString());
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }


    @GetMapping("/hello")
    public String getName() {
        var name = "Kedar3";
        return name;
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public String getProductById(@PathVariable Long productId) {
                return "Product with ID: " + productId;
    }

    // Handles POST requests to /products
    @PostMapping
    @ResponseBody
    public String createProduct(@RequestParam String name) {
        return "Created product: " + name;
    }

    /*If the request body is in JSON format (e.g., {"username": "john.doe", "email": "john.doe@example.com"}),
    Spring's default Jackson2HttpMessageConverter will automatically deserialize it into a User object.
    If fields from input JSON and POJO do not match  - you will get HTTP400 - bad request

    If User class field names are not exactly matched to your POJO, you can map JSON fields to POJO variables like below
    public class User {
    @JsonProperty("user_email")
    private String email;
    }   */
    @PostMapping(path = "/create/{contName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user,
                                     @RequestHeader Map<String, String> headers,
                                     @PathVariable("contName") String continentName) {
        System.out.println("Received user: " + user.toString());
        User retUser = new User(); user.setName("Kedar");
        System.out.println("Just Printing");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Custom-Header", "CustomHeaderValue");
        return new ResponseEntity<User>(retUser, responseHeaders, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("Updating user: " + user.toString());
        return new ResponseEntity<User>(userService.updateUserByEmail(user), HttpStatus.OK);
    }

    @PutMapping(path = "/update/userbyBokadaka")
    public ResponseEntity<User> updateUserByBokadaka(@RequestBody User user) {
        System.out.println("Updating user: " + user.toString());
        return new ResponseEntity<User>(userService.updateUserByBokadaka(user), HttpStatus.OK);
    }



}
