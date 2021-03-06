package com.demcare.demo.dao;

import com.demcare.demo.entities.Game;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameDao extends CrudRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    Game save(Game game);
    Game findByTitulo(String titulo);
    Optional<Game> findById(Long id);
    Iterable<Game> findAll();

}
