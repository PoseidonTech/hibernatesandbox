package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.domain.ManyObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManyObjectRepository extends CrudRepository<ManyObject, Integer> {
    ManyObject getByNameEquals(String name);
}
