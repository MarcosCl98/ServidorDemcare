package com.demcare.demo.dao;

import com.demcare.demo.entities.Token;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDao extends CrudRepository<Token, Long>, JpaSpecificationExecutor<Token> {
    Token save(Token token);
    Token findByUser(User user);
    Token findByCode(String tokenCode);

}
