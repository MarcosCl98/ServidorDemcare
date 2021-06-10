package com.demcare.demo.service;

import com.demcare.demo.entities.Data;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {
    Data save(Data data);
    List<Data> findByUser(User user);

}
