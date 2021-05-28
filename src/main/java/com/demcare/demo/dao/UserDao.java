package com.demcare.demo.dao;

import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByMail(String mail);
    User findByName(String name);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    User save(User user);

}
