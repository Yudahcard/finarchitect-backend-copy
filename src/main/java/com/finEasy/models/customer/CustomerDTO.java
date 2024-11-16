package com.finEasy.models.customer;

import lombok.Data;


@Data
public class CustomerDTO {

    private String username;
    private String email;
    private String password;

    private String firstName;

    private String lastName;

    private String Country;
}
