package com.finEasy.models.customer;

import com.finEasy.models.repository.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtCustomerDetailsService implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private CustomerRepo customerRepo;
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


        Customer customer = customerRepo.findByUsername(username);

        if(customer ==null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else{
            return new User(customer.getUsername(),customer.getPassword(),new ArrayList<>());
        }

//        if ("javainuse".equals(username)) {
//
//
//            logger.info(">>>>>>>>>javainuse user found with username<<<<<<<<<<<<<<");
//
//
//
//            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());
//        } else {
//
//            logger.info(">>>>>>>>>User not found with username<<<<<<<<<<<<<<");
//
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }

//    public Customer save(CustomerDTO user) {
//
//        Customer newCustomer = new Customer();
//
//
//        newCustomer.setUsername(user.getUsername());
//        newCustomer.setPassword(bcryptEncoder.encode(user.getPassword()));
//        newCustomer.setEmail(user.getEmail());
//        newCustomer.setFirstName(user.getFirstName());
//        newCustomer.setLastName(user.getLastName());
//        newCustomer.setCountry(user.getCountry());
//
//
//        return customerRepo.save(newCustomer);
//    }

    public String save(CustomerDTO customerDTO) {

        Customer newCustomer = new Customer();

        newCustomer.setUsername(customerDTO.getUsername());
        newCustomer.setPassword(bcryptEncoder.encode(customerDTO.getPassword()));
        newCustomer.setEmail(customerDTO.getEmail());
        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setCountry(customerDTO.getCountry());

        customerRepo.save(newCustomer);

        return "New customer saved successfully";
    }

}
