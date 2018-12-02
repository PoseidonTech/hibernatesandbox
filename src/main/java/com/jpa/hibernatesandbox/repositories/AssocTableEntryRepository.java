package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.domain.AssocTableEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssocTableEntryRepository extends CrudRepository<AssocTableEntry, Integer> {
}
