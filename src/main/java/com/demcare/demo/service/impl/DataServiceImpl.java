package com.demcare.demo.service.impl;

import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.DataService;
import com.demcare.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    DataDao dataDao;

    @Override
    public Data save(Data data) {
        return dataDao.save(data);
    }

    @Override
    public List<Data> findByUser(User user) {
        return dataDao.findByUser(user);
    }


}
