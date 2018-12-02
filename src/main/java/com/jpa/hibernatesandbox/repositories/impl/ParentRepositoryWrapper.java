package com.jpa.hibernatesandbox.repositories.impl;

import com.jpa.hibernatesandbox.domain.Parent;
import com.jpa.hibernatesandbox.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentRepositoryWrapper {

    @Autowired
    private ParentRepository parentRepository;

    public Parent getParentByParentId(Integer parentId) {
        return parentRepository.getParentByParentId(parentId);
    }
}
