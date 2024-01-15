package com.lumberlogic.web;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}-{password}")
    public ResponseEntity<Optional<User>> getSingleUser(
            @PathVariable String username,
            @PathVariable String password) {
        // Please note that passing sensitive information in the URL is not secure.
        // For development purposes only!

        return new ResponseEntity<>(userService.singleUser(username, password), HttpStatus.OK);
    }


}
