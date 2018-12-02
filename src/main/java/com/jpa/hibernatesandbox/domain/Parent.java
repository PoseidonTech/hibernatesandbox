package com.jpa.hibernatesandbox.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jpa.hibernatesandbox.helper.EntityListSerializer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer parentId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    @OrderBy(value = "childId ASC")
//    @OrderColumn
//    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Child> childList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @OrderColumn
//    @OrderBy(value = "step_child_id")
    @JoinTable(name = "parent_manyobject",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "many_object_id"))
    @JsonSerialize(contentUsing = EntityListSerializer.class)
    private List<ManyObject> manyObjectList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    @OrderBy(value = "assocTableId")
//    @OrderColumn
    private List<AssocTableEntry> assocTableEntryList = new ArrayList<>();

    public Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //helper methods
    public void addToChildList(Child child) {
        child.setParent(this);
        childList.add(child);
    }

    public void addToChildList(List<Child> childList) {
        for (Child child : childList) {
            child.setParent(this);
        }
        this.childList.addAll(childList);
    }

    public void removeFromChildList(Child child) {
        child.setParent(null);
        childList.remove(child);
    }

    public void removeFromChildList(List<Child> childList) {
        for (Child child : childList) {
            child.setParent(null);
        }
        this.childList.removeAll(childList);
    }

    public void clearChildList() {
        childList.clear();
    }

    public List<ManyObject> getManyObjectList() {
        return manyObjectList;
    }

    public void addToManyObjectList(ManyObject manyObject) {
        manyObject.addToParentList(this);
        manyObjectList.add(manyObject);
    }

    public void addToManyObjectList(List<ManyObject> manyObjectList) {
        for (ManyObject manyObject : manyObjectList) {
            manyObject.addToParentList(this);
        }
        this.manyObjectList.addAll(manyObjectList);
    }

    public void removeFromManyObjectList(ManyObject manyObject) {
        manyObject.removeFromParentList(this);
        manyObjectList.remove(manyObject);
    }

    public void removeFromManyObjectList(List<ManyObject> manyObjectList) {
        for (ManyObject manyObject : manyObjectList) {
            manyObject.removeFromParentList(this);
        }
        this.manyObjectList.removeAll(manyObjectList);
    }

    public void clearAssocTableList() {
        this.assocTableEntryList.clear();
    }

    public List<AssocTableEntry> getAssocTableEntryList() {
        return assocTableEntryList;
    }

    public void addToAssocTableList(AssocTableEntry assocTableEntry) {
        assocTableEntry.setParent(this);
        this.assocTableEntryList.add(assocTableEntry);
    }

    public void addToAssocTableList(List<AssocTableEntry> assocTableEntryList) {
        for (AssocTableEntry assocTableEntry : assocTableEntryList) {
            assocTableEntry.setParent(this);
        }
        this.assocTableEntryList.addAll(assocTableEntryList);
    }

    public void removeFromAssocTableList(AssocTableEntry assocTableEntry) {
        this.assocTableEntryList.remove(assocTableEntry);
    }

    public void removeFromAssocTableList(List<AssocTableEntry> assocTableEntryList) {
        for (AssocTableEntry assocTableEntry : assocTableEntryList) {
            assocTableEntry.setParent(this);
        }
        this.assocTableEntryList.removeAll(assocTableEntryList);
    }
}
