package com.jpa.hibernatesandbox.services.impl;

import com.jpa.hibernatesandbox.domain.Child;
import com.jpa.hibernatesandbox.repositories.ChildRepository;
import com.jpa.hibernatesandbox.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Override
    public Iterable<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @Override
    public Child getChildById(Integer id) {
        return childRepository.findOne(id);
    }

    @Override
    public Child saveChild(Child child) {
        return childRepository.save(child);
    }

    @Override
    public void deleteChild(Integer id) {
        childRepository.delete(id);
    }
}
