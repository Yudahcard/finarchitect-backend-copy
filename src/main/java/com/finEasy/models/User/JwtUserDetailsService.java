package com.finEasy.models.User;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;





public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private DAOUser daoUser;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private UserDAORepo userDAORepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DAOUser daouser = userDAORepo.findByUsername(username);

        if(daouser == null){

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        else {
            return new User(daouser.getUsername(),daouser.getPassword(),
                    new ArrayList<>());
        }
    }

    public DAOUser save(UserDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDAORepo.save(newUser);
    }

}
