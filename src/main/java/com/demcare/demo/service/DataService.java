package com.demcare.demo.service;

import com.demcare.demo.entities.Data;
import com.demcare.demo.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {
    Data save(Data data);

}
