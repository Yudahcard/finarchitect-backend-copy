package com.finEasy.models.customer;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.finEasy.models.customer.DAOUser;




@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private UserDAORepo userDAORepo;
//    @Autowired
//    private DAOUser daoUser;

    @Autowired
    private PasswordEncoder bcryptEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        DAOUser daouser = userDAORepo.findByUsername(username);
//
//        if(daouser == null){
//
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        else {
//            return new User(daouser.getUsername(),daouser.getPassword(),
//                    new ArrayList<>());
//        }


        if ("javainuse".equals(username)) {


            logger.info(">>>>>>>>>javainuse user found with username<<<<<<<<<<<<<<");



            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {

            logger.info(">>>>>>>>>User not found with username<<<<<<<<<<<<<<");

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public DAOUser save(UserDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDAORepo.save(newUser);
    }




//    public String save(UserDTO user) {
//        DAOUser newUser = new DAOUser();
//        newUser.setUsername(user.getUsername());
////        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//
//        userDAORepo.save(newUser);
//
//        return "New customer saved successfully";
//    }

}
