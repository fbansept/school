package edu.fbansept.school.security;

import edu.fbansept.school.model.Administrateur;
import edu.fbansept.school.model.Role;
import edu.fbansept.school.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> listAuthorities = new ArrayList<>();

        if(this.user instanceof Administrateur) {
            listAuthorities.add(
                    new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR")
            );
        }

        listAuthorities.add(
                new SimpleGrantedAuthority("ROLE_UTILISATEUR")
        );

//        for(Role role : this.user.getListRole()){
//            listAuthorities.add(
//                    new SimpleGrantedAuthority(role.getName())
//            );
//        }
//
//        listAuthorities.add(
//                new SimpleGrantedAuthority("ROLE_UTLISATEUR")
//        );

        return listAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
