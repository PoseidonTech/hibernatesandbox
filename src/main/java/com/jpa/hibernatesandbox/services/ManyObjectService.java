package com.jpa.hibernatesandbox.services;

import com.jpa.hibernatesandbox.domain.ManyObject;

public interface ManyObjectService {
    Iterable<ManyObject> getAllManyObjects();
    ManyObject getManyObjectById(Integer manyObjectId);
    ManyObject saveManyObject(ManyObject manyObject);
    void deleteManyObject(Integer manyObjectId);
}
