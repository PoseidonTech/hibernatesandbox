package com.jpa.hibernatesandbox.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.hibernatesandbox.domain.Child;
import com.jpa.hibernatesandbox.domain.ManyObject;
import com.jpa.hibernatesandbox.domain.Parent;
import com.jpa.hibernatesandbox.repositories.impl.ParentRepositoryWrapper;
import com.jpa.hibernatesandbox.services.ManyObjectService;
import com.jpa.hibernatesandbox.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Controller
public class ParentController {

    @Autowired
    private ParentRepositoryWrapper parentRepositoryWrapper;
    @Autowired
    private ParentService parentService;
    @Autowired
    private ManyObjectService manyObjectService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/parent")
    public ResponseEntity<List<Parent>> getAllParents() {
        return new ResponseEntity<>((List<Parent>) parentService.getAllParents(), HttpStatus.OK);
    }

    @GetMapping(value = "/parent/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable(value = "id") Integer parentId) {

        Parent parent = parentRepositoryWrapper.getParentByParentId(parentId);
//        Parent parent = parentService.getParentById(parentId);
//        System.out.println("parent: "+parent.getChildList().size());

//        return new ResponseEntity<>(parentRepositoryWrapper.getParentByParentId(parentId), HttpStatus.OK);
        return new ResponseEntity<>(parent, HttpStatus.OK);
    }

    @PostMapping(value = "/parent/{id}/addChild", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Child> mapChild(@PathVariable(value = "id") Integer parentId, @RequestBody Child child) {

        Parent parent = parentService.getParentById(parentId);
        parent.addToChildList(child);
        parentService.saveParent(parent);

        return new ResponseEntity<>(child, HttpStatus.OK);
    }

    @PostMapping(value = "/addDaisy")
    public ResponseEntity addDaisy(@RequestBody String input) throws IOException {
        JsonNode inputJson = objectMapper.readTree(input);
        Integer parentId = new Integer(inputJson.findValue("parentId").asText());

        Parent parent = parentService.getParentById(parentId);
        Child daisy = new Child("Daisy");
        parent.addToChildList(daisy);
        parentService.saveParent(parent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/orphanDaisy")
    public ResponseEntity orphanDaisy(@RequestBody String input) throws IOException {
        JsonNode inputJson = objectMapper.readTree(input);
        Integer parentId = new Integer(inputJson.findValue("parentId").asText());

        Parent parent = parentService.getParentById(parentId);
        Child daisy = null;
        for (Child child : parent.getChildList()) {
            if ("Daisy".equals(child.getName())) {
                daisy = child;
            }
        }

        if (daisy != null) {
            parent.removeFromChildList(daisy);
        }

        parentService.saveParent(parent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/addManyObjects")
    public ResponseEntity addManyObjects() {

        Parent parent = parentService.getParentById(1);

        ManyObject manyObject1 = new ManyObject("red");
        ManyObject manyObject2 = new ManyObject("white");
        ManyObject manyObject3 = new ManyObject("blue");

        parent.addToManyObjectList(manyObject1);
        parent.addToManyObjectList(manyObject2);
        parent.addToManyObjectList(manyObject3);

        parentService.saveParent(parent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/orphanMany")
    public ResponseEntity orphanMany() {

        ManyObject manyObjectRed = manyObjectService.getManyObjectById(1);
        ManyObject manyObjectWhite = manyObjectService.getManyObjectById(2);

        Parent parent = parentService.getParentById(2);

        parent.removeFromManyObjectList(manyObjectRed);
        parent.addToManyObjectList(manyObjectWhite);

        parentService.saveParent(parent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/addChildList")
    public ResponseEntity addChildList(@RequestBody List<Child> childList) {

        Parent parent = parentService.getParentById(1);

        parent.clearChildList();
        parent.addToChildList(childList);

        parentService.saveParent(parent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/manyObject/{id}")
    public ResponseEntity<ManyObject> getManyObjectById(@PathVariable(value = "id") Integer manyObjectId) {
        return new ResponseEntity<>(manyObjectService.getManyObjectById(manyObjectId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/parent/child/{id}")
    public ResponseEntity deleteChildById(@PathVariable(value = "id") Integer childId) {
        Parent parent = parentService.getParentById(1);
        for(Child child : parent.getChildList()) {
            if(childId.equals(child.getChildId())) {
                parent.removeFromChildList(child);
                break;
            }
        }
        parentService.saveParent(parent);
        return new ResponseEntity(HttpStatus.OK);
    }
}
