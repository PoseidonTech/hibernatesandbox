package com.jpa.hibernatesandbox.services.impl;

import com.jpa.hibernatesandbox.domain.Parent;
import com.jpa.hibernatesandbox.repositories.ParentRepository;
import com.jpa.hibernatesandbox.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public Iterable<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentById(Integer id) {
        return parentRepository.findOne(id);
    }

    @Override
    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public void deleteParent(Integer id) {
        parentRepository.delete(id);
    }
}
