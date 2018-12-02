package com.jpa.hibernatesandbox.services;

import com.jpa.hibernatesandbox.domain.Parent;

public interface ParentService {
    Iterable<Parent> getAllParents();
    Parent getParentById(Integer id);
    Parent saveParent(Parent parent);
    void deleteParent(Integer id);
}
