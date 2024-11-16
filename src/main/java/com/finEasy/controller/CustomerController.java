package com.finEasy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finEasy.models.customer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CustomerController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtCustomerDetailsService jwtCustomerDetailsService;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    //    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @PostMapping(value = "/login", consumes = "text/plain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody String authenticationRequest) throws Exception {

        System.out.println("We're in authenticate controller");
        JwtRequest jwtRequest = objectMapper.readValue(authenticationRequest, JwtRequest.class);

        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        }

        catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }


        final UserDetails userDetails = jwtCustomerDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        System.out.println(">>>>>>>>>>>>> Token generated " + token);

        return ResponseEntity.ok(new JwtResponse(token));


    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody CustomerDTO user) throws Exception {

        return ResponseEntity.ok(jwtCustomerDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {

            System.out.println("Entering authenticate() method with username: " + username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("Authentication successful for username: " + authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
            System.out.println("Authentication successful for username: " + username);

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
