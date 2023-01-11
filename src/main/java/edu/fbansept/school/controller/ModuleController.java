package edu.fbansept.school.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.school.dao.ModuleDao;
import edu.fbansept.school.model.Module;
import edu.fbansept.school.view.ModuleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ModuleController {
    @Autowired
    private ModuleDao moduleDao;

    @GetMapping("/modules")
    @JsonView(ModuleView.class)
    public List<Module> getAllModule() {
        return moduleDao.findAll();
    }

    @GetMapping("/module/{id}")
    @JsonView(ModuleView.class)
    public ResponseEntity<Module> getModuleById(@PathVariable int id) {

        Optional<Module> module = moduleDao.findById(id);

        if(module.isPresent()){
            return new ResponseEntity<>(module.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/module")
    public ResponseEntity<Module> saveModule(@RequestBody Module module) {

        //Si l'utilisateur fourni n'a pas toutes les informations
        if(module == null || module.getName().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Si l'id est fourni
        if(module.getId() != null) {
            Optional<Module> moduleDatabase = moduleDao.findById(module.getId());
            //Si il n'existe pas dans la base de donnée
            if(moduleDatabase.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //L'id est fourni et l'utilisateur existe, c'est donc un update
            moduleDao.save(module);

            return new ResponseEntity<>(module,HttpStatus.OK);
        }

        //l'id n'est pas fourni c'est donc un CREATE
        moduleDao.save(module);

        return new ResponseEntity<>(module, HttpStatus.CREATED);
    }

    @DeleteMapping("/module/{id}")
    public ResponseEntity<Module> deleteModule(@PathVariable int id) {

        Optional<Module> module = moduleDao.findById(id);

        if(module.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        moduleDao.deleteById(id);

        return new ResponseEntity<>(module.get(), HttpStatus.OK);
    }



}
