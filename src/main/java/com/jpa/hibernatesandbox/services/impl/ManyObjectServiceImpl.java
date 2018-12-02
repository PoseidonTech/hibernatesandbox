package com.jpa.hibernatesandbox.services.impl;

import com.jpa.hibernatesandbox.domain.ManyObject;
import com.jpa.hibernatesandbox.repositories.ManyObjectRepository;
import com.jpa.hibernatesandbox.services.ManyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManyObjectServiceImpl implements ManyObjectService {

    @Autowired
    private ManyObjectRepository manyObjectRepository;

    @Override
    public Iterable<ManyObject> getAllManyObjects() {
        return manyObjectRepository.findAll();
    }

    @Override
    public ManyObject getManyObjectById(Integer manyObjectId) {
        return manyObjectRepository.findOne(manyObjectId);
    }

    @Override
    public ManyObject saveManyObject(ManyObject manyObject) {
        return manyObjectRepository.save(manyObject);
    }

    @Override
    public void deleteManyObject(Integer manyObjectId) {
        manyObjectRepository.delete(manyObjectId);
    }
}
