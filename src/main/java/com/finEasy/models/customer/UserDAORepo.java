package com.finEasy.models.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAORepo extends CrudRepository<DAOUser,Integer> {

    DAOUser findByUsername(String username);

}