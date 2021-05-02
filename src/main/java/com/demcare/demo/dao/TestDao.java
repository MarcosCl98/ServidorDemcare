package com.demcare.demo.dao;

import com.demcare.demo.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends CrudRepository<TestEntity, Long>, JpaSpecificationExecutor<TestEntity> {

}
