package com.finEasy.models.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

//    public JwtResponse(String jwttoken) {
//        this.jwttoken = jwttoken;
//    }
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;



}
