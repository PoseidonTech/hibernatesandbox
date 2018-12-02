package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.domain.StepChild;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepChildRepository extends CrudRepository<StepChild, Integer> {
}
