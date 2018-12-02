package com.jpa.hibernatesandbox.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jpa.hibernatesandbox.helper.EntitySerializer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AssocTableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assoc_table_id")
    private Integer assocTableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonSerialize(using = EntitySerializer.class)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "step_child_id")
    @JsonSerialize(using = EntitySerializer.class)
    private StepChild stepChild;

    public Integer getAssocTableId() {
        return assocTableId;
    }

    public void setAssocTableId(Integer assocTableId) {
        this.assocTableId = assocTableId;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public StepChild getStepChild() {
        return stepChild;
    }

    public void setStepChild(StepChild stepChild) {
        this.stepChild = stepChild;
    }
}
