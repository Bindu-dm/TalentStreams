package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.User;
import com.example.demo.service.UserService;

@CrossOrigin("*")
@RestController
public class UserController {

	@Autowired
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
    	System.out.println(user.getMobilenumber());
    	User existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser == null) {
        	User registeredUser = userService.registerUser(user);
            if(registeredUser!=null) {
            	System.out.println("registerd successfully");
            	return ResponseEntity.ok("registerd successfully");
            } 
        }
        else {
        	System.out.println("Email already exit");
            return ResponseEntity.status(401).body("Email already exit");
        }
    	
    	
        
        System.out.println("unable to register");
        return ResponseEntity.status(401).body("unable to register");
    }


    	@PostMapping("/login")
    	public ResponseEntity<String> loginUser(@RequestBody User user) {
    		System.out.println(user.getEmail());
            User existingUser = userService.getUserByEmail(user.getEmail());
            
         // Check if the existingUser is null
            if (existingUser == null) {
                System.out.println("User with email " + user.getEmail() + " not found");
                return ResponseEntity.badRequest().body("User with email " + user.getEmail() + " not found");
            }
            System.out.println(user.getPassword());
            // Check if the password matches
            if (!user.getPassword().equals(existingUser.getPassword())) {
                System.out.println("Invalid email or password for user: " + existingUser.getEmail());
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            // Login successful
            return ResponseEntity.ok("Login successful");
        }
    	@PostMapping("/adminlogin")
        public ResponseEntity<String> adminlogin(@RequestBody User user) {


            boolean b=userService.adminLogin(user);

            if(b) {
                return new ResponseEntity<>("success",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
            }
        }

    }



