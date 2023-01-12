package edu.fbansept.school.security;

import edu.fbansept.school.dao.UserDao;
import edu.fbansept.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userDao.findByEmail(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Email inconnu");
        }

        return new MyUserDetails(user.get());
    }
}
