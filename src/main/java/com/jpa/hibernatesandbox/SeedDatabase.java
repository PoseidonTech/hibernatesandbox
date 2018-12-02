package com.jpa.hibernatesandbox;

import com.jpa.hibernatesandbox.domain.AssocTableEntry;
import com.jpa.hibernatesandbox.domain.Child;
import com.jpa.hibernatesandbox.domain.ManyObject;
import com.jpa.hibernatesandbox.domain.Parent;
import com.jpa.hibernatesandbox.domain.StepChild;
import com.jpa.hibernatesandbox.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SeedDatabase implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        seedDb();
    }


    public void seedDb() {

        Parent steve = new Parent();

        steve.setName("Steve");

        //add Child objects (one to many)
        Child child1 = new Child("Huey");
        Child child2 = new Child("Dewey");
        Child child3 = new Child("Louie");

        steve.addToChildList(child1);
        steve.addToChildList(child2);
        steve.addToChildList(child3);

        //add ManyObjects (many to many)
        ManyObject red = new ManyObject("Red");
        ManyObject white = new ManyObject("White");
        ManyObject blue = new ManyObject("Blue");

        steve.addToManyObjectList(red);
        steve.addToManyObjectList(white);
        steve.addToManyObjectList(blue);

        //add AssocTable objects (many to many assoc table)
        StepChild dale = new StepChild("Dale");
        StepChild tucker = new StepChild("Tucker");
        StepChild zoonie = new StepChild("Zoonie");

        AssocTableEntry daleEntry = new AssocTableEntry();
        daleEntry.setStepChild(dale);
        AssocTableEntry tuckerEntry = new AssocTableEntry();
        tuckerEntry.setStepChild(tucker);
        AssocTableEntry zoonieEntry = new AssocTableEntry();
        zoonieEntry.setStepChild(zoonie);

        steve.addToAssocTableList(daleEntry);
        steve.addToAssocTableList(tuckerEntry);
        steve.addToAssocTableList(zoonieEntry);

        parentRepository.save(steve);
    }

}
