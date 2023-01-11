package edu.fbansept.school.controller;

import edu.fbansept.school.dao.RoleDao;
import edu.fbansept.school.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {
    @Autowired
    private RoleDao roleDao;

    @GetMapping("/roles")
    public List<Role> getAllRole() {
        return roleDao.findAll();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {

        Optional<Role> role = roleDao.findById(id);

        if(role.isPresent()){
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {

        //Si l'utilisateur fourni n'a pas toutes les informations
        if(role == null || role.getName().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Si l'id est fourni
        if(role.getId() != null) {
            Optional<Role> roleDatabase = roleDao.findById(role.getId());
            //Si il n'existe pas dans la base de donnée
            if(roleDatabase.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //L'id est fourni et l'utilisateur existe, c'est donc un update
            roleDao.save(role);

            return new ResponseEntity<>(role,HttpStatus.OK);
        }

        //l'id n'est pas fourni c'est donc un CREATE
        roleDao.save(role);

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable int id) {

        Optional<Role> role = roleDao.findById(id);

        if(role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);

        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }



}
