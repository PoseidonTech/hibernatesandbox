package com.jpa.hibernatesandbox.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jpa.hibernatesandbox.helper.EntityListSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StepChild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "step_child_id")
    private Integer stepChildId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stepChild")
    @JsonSerialize(contentUsing = EntityListSerializer.class)
    private List<AssocTableEntry> assocTableEntryList = new ArrayList<>();

    private String name;

    public StepChild() {}

    public StepChild(String name) {
        this.setName(name);
    }

    public Integer getStepChildId() {
        return stepChildId;
    }

    public void setStepChildId(Integer stepChildId) {
        this.stepChildId = stepChildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clearAssocTableList() {
        this.assocTableEntryList.clear();
    }

    public List<AssocTableEntry> getAssocTableEntryList() {
        return assocTableEntryList;
    }

    public void addToAssocTableList(AssocTableEntry assocTableEntry) {
        assocTableEntry.setStepChild(this);
        this.assocTableEntryList.add(assocTableEntry);
    }

    public void addToAssocTableList(List<AssocTableEntry> assocTableEntryList) {
        for(AssocTableEntry assocTableEntry : assocTableEntryList) {
            assocTableEntry.setStepChild(this);
        }
        this.assocTableEntryList.addAll(assocTableEntryList);
    }
}
