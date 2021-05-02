package com.demcare.demo.delegate.impl;

import com.demcare.demo.service.TestService;
import com.demcare.demo.model.TestModel;
import com.demcare.demo.delegate.TestDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDelegateImpl implements TestDelegate {

    @Autowired
    private TestService testService;

    @Override
    public TestModel findTestModel(Long id) {
        //return testService.findTestModel(id);
        return null;
    }
}
