package com.jguest.explorelearning.challenge.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll(Sort.by("lastName")).forEach(users::add);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error finding all users", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error finding user by id {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestBody userReqBody) {
        try {
            User user = userRepository.save(new User(userReqBody.getFirstName(), userReqBody.getLastName()));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error saving user", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {
                userRepository.delete(user.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
