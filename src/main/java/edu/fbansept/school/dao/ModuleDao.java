package edu.fbansept.school.dao;

import edu.fbansept.school.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleDao extends JpaRepository<Module, Integer> {

}
