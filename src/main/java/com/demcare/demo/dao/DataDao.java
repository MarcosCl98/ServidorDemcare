package com.demcare.demo.dao;

import com.demcare.demo.entities.Data;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataDao extends CrudRepository<Data, Long>, JpaSpecificationExecutor<Data> {
    Data save(Data data);
    List<Data> findByUser(User user);
}
