package com.jpa.hibernatesandbox.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jpa.hibernatesandbox.helper.EntityListSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ManyObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "many_object_id")
    private Integer manyObjectId;

    private String name;

    @ManyToMany(mappedBy = "manyObjectList", fetch = FetchType.LAZY)
    @JsonSerialize(contentUsing = EntityListSerializer.class)
    private List<Parent> parentList = new ArrayList<>();

    public ManyObject() {
    }

    public ManyObject(String name) {
        this.setName(name);
    }

    public Integer getManyObjectId() {
        return manyObjectId;
    }

    public void setManyObjectId(Integer manyObjectId) {
        this.manyObjectId = manyObjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public void addToParentList(Parent parent) {
        parentList.add(parent);
    }

    public void removeFromParentList(Parent parent) {
        parentList.remove(parent);
    }
}
