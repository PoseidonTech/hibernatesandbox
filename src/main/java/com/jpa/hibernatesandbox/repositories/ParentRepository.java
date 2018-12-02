package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.constants.Queries;
import com.jpa.hibernatesandbox.domain.Parent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer> {
    Parent getByNameEquals(String name);

    @Query(Queries.GET_PARENT_BY_PARENT_ID)
    Parent getParentByParentId(@Param("parentId") Integer parentId);
}
