package jwei26.controller;

import jwei26.model.User;
import jwei26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5177")
@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity userRegister(@RequestBody User user) {
        try{
            userService.registerUser(user);
            return ResponseEntity.ok().body("Success to register your account!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Registration error: " + e.getMessage());
        }
    }
}
