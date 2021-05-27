package com.demcare.demo.dao;


import com.demcare.demo.entities.Games;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesDao extends CrudRepository<Games, Long>, JpaSpecificationExecutor<Games> {

}
