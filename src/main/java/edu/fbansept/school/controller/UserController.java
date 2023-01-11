package edu.fbansept.school.controller;

import edu.fbansept.school.dao.UserDao;
import edu.fbansept.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {

        Optional<User> user = userDao.findById(id);

        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        //Si l'utilisateur fourni n'a pas toutes les informations
        if(user == null || user.getEmail().equals("") || user.getPassword().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Si l'id est fourni
        if(user.getId() != null) {
            Optional<User> userDatabase = userDao.findById(user.getId());
            //Si il n'existe pas dans la base de donnée
            if(userDatabase.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }


        return userDao.save(user);
    }

    @DeleteMapping("/user/{id}")
    public User deleteUser(@PathVariable int id) {

        User user = userDao.findById(id).orElse(null);

        userDao.deleteById(id);
        return user;
    }



}
