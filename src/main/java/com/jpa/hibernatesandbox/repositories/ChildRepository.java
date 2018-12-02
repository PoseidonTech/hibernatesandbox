package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.domain.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends CrudRepository<Child, Integer> {
    Child getByNameEquals(String name);
}
