package com.demcare.demo.controller;

import com.demcare.demo.service.TestService;
import com.demcare.demo.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/test")
public class TestController extends DemcareController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TestModel>> getAll(){
        List<TestModel> listAllTestModel = testService.listAll();
        return new ResponseEntity<>(listAllTestModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestModel> getTest(@RequestParam @PathVariable Long id){
        validateRequiredParam(id, "id");

        TestModel testModel = testService.getById(id);
        return new ResponseEntity<>(testModel, HttpStatus.OK);
    }
}
