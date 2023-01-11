package edu.fbansept.school.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.school.dao.UserDao;
import edu.fbansept.school.model.User;
import edu.fbansept.school.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/users")
    @JsonView(UserView.class)
    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    @JsonView(UserView.class)
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
            //L'id est fourni et l'utilisateur existe, c'est donc un update
            userDao.save(user);

            return new ResponseEntity<>(user,HttpStatus.OK);
        }

        //l'id n'est pas fourni c'est donc un CREATE
        userDao.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {

        Optional<User> user = userDao.findById(id);

        if(user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDao.deleteById(id);

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }



}
