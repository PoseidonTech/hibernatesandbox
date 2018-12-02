package com.jpa.hibernatesandbox.services;

import com.jpa.hibernatesandbox.domain.Child;

public interface ChildService {
    Iterable<Child> getAllChildren();
    Child getChildById(Integer id);
    Child saveChild(Child child);
    void deleteChild(Integer id);
}
