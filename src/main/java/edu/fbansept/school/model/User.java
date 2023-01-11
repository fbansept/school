package edu.fbansept.school.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.school.view.ModuleView;
import edu.fbansept.school.view.UserView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({UserView.class, ModuleView.class})
    private Integer id;

    @JsonView({UserView.class, ModuleView.class})
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JsonView(UserView.class)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_module",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    @JsonView(UserView.class)
    private Set<Module> listModule = new HashSet<>();



}
