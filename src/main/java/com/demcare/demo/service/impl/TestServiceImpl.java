package com.demcare.demo.service.impl;

import com.demcare.demo.dao.TestDao;
import com.demcare.demo.entities.TestEntity;
import com.demcare.demo.service.TestService;
import com.demcare.demo.model.TestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<TestModel> listAll() {
        return testDao.findAll(null)
                .stream().parallel()
                .map(testEntity -> modelMapper.map(testEntity, TestModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public TestModel getById(Long id) {
        Optional<TestEntity> test = testDao.findById(id);
        TestModel testModel = modelMapper.map(test.get(), TestModel.class);
        return testModel;
    }

    @Override
    public TestModel saveOrUpdate(TestModel domainObject) {
        TestEntity testEntity = testDao.save(modelMapper.map(domainObject, TestEntity.class));
        return modelMapper.map(testDao.save(testEntity), TestModel.class);
    }

    @Override
    public void delete(Long id) {
        testDao.deleteById(id);
    }
}
