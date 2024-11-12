package com.finEasy.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserDAORepo extends CrudRepository<DAOUser,Integer> {

    DAOUser findByUsername(String username);

}