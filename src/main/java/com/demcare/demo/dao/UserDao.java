package com.demcare.demo.dao;

import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByMail(String mail);

    void deleteById(Long id);


}
