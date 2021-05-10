package com.demcare.demo.dao;


import com.demcare.demo.entities.AsociatedUser;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsociatedUserDao extends CrudRepository<AsociatedUser, Long>, JpaSpecificationExecutor<AsociatedUser> {

   /* List<User> findAsociatedUsersByUserInstitution(User userInstitution);*/

    List<AsociatedUser> findAll();

}
