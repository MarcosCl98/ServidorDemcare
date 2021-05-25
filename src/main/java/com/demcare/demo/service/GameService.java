package com.demcare.demo.service;

import com.demcare.demo.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    Game findByTitulo(String titulo);
    List<Game> findAll();
    Game save(Game game);

}
