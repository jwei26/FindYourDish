package jwei26.controller;

import jwei26.service.JWTService;
import jwei26.service.UserService;
import jwei26.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    JWTService jwtService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestBody User user) {
        try{
            User u = userService.getUserByCredientials(user.getEmail(), user.getPassword());
            if(u == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid credentials");
            }
            return ResponseEntity.ok().body(jwtService.generateToken(u));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login error: " + e.getMessage());
        }
    }
}
