package com.jpa.hibernatesandbox.repositories;

import com.jpa.hibernatesandbox.HibernateSandbox;
import com.jpa.hibernatesandbox.domain.AssocTableEntry;
import com.jpa.hibernatesandbox.domain.Child;
import com.jpa.hibernatesandbox.domain.ManyObject;
import com.jpa.hibernatesandbox.domain.Parent;
import com.jpa.hibernatesandbox.domain.StepChild;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HibernateSandbox.class)
@Transactional
public class ParentDaoTest {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ManyObjectRepository manyObjectRepository;
    @Autowired
    private AssocTableEntryRepository assocTableEntryRepository;
    @Autowired
    private StepChildRepository stepChildRepository;

    //region OneToOne Tests
    @Test
    public void addOneChildTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        Child daisy = new Child("Daisy");
        steve.addToChildList(daisy);
        steve = parentRepository.save(steve);

        assertEquals(4, steve.getChildList().size());
    }

    @Test
    public void addChildListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        Child theodore = new Child("Theodore");
        Child thomas = new Child("Thomas");
        Child andrew = new Child("Andrew");

        List<Child> childList = new ArrayList<>();
        childList.add(theodore);
        childList.add(thomas);
        childList.add(andrew);

        steve.addToChildList(childList);
        steve = parentRepository.save(steve);

        assertEquals(6, steve.getChildList().size());
    }

    @Test
    public void removeOneChildTest() {
        Parent steve = parentRepository.getParentByParentId(1);
        Child louie = childRepository.getByNameEquals("Louie");

        steve.removeFromChildList(louie);
        steve = parentRepository.save(steve);

        assertEquals(2, steve.getChildList().size());
        //test orphan removal
        assertEquals(2, ((List<Child>) childRepository.findAll()).size());
    }

    @Test
    public void removeChildListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        Child huey = childRepository.getByNameEquals("Huey");
        Child dewey = childRepository.getByNameEquals("Dewey");

        List<Child> childList = new ArrayList<>();
        childList.add(huey);
        childList.add(dewey);

        steve.removeFromChildList(childList);
        steve = parentRepository.save(steve);

        assertEquals(1, steve.getChildList().size());
        //test orphan removal
        assertEquals(1, ((List<Child>) childRepository.findAll()).size());
    }

    @Test
    public void clearChildListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        steve.clearChildList();
        steve = parentRepository.save(steve);

        assertEquals(0, steve.getChildList().size());
        //test orphan removal
        assertEquals(0, ((List<Child>) childRepository.findAll()).size());
    }
    //endregion

    //region ManyToMany Tests
    @Test
    public void addOneManyObjectTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        ManyObject green = new ManyObject("Green");
        steve.addToManyObjectList(green);
        steve = parentRepository.save(steve);

        assertEquals(4, steve.getManyObjectList().size());
    }

    @Test
    public void addManyObjectListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        ManyObject black = new ManyObject("Black");
        ManyObject orange = new ManyObject("Orange");

        List<ManyObject> manyObjectList = new ArrayList<>();
        manyObjectList.add(black);
        manyObjectList.add(orange);

        steve.addToManyObjectList(manyObjectList);

        assertEquals(5, steve.getManyObjectList().size());
    }

    @Test
    public void removeManyObjectTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        ManyObject red = manyObjectRepository.getByNameEquals("Red");

        steve.removeFromManyObjectList(red);
        steve = parentRepository.save(steve);

        assertEquals(2, steve.getManyObjectList().size());
    }

    @Test
    public void removeManyObjectListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        ManyObject white = manyObjectRepository.getByNameEquals("White");
        ManyObject blue = manyObjectRepository.getByNameEquals("Blue");

        List<ManyObject> manyObjectList = new ArrayList<>();
        manyObjectList.add(white);
        manyObjectList.add(blue);

        steve.removeFromManyObjectList(manyObjectList);
        steve = parentRepository.save(steve);

        assertEquals(1, steve.getManyObjectList().size());
    }
    //endregion

    //region assoc table manytomany
    @Test
    public void clearAssocTableTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        steve.clearAssocTableList();

        assertEquals(0, steve.getAssocTableEntryList().size());
        //test orphan removal
        assertEquals(0, ((List<AssocTableEntry>) assocTableEntryRepository.findAll()).size());
    }

    @Test
    public void addOneToAssocTableTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        StepChild leif = new StepChild();
        AssocTableEntry leifEntry = new AssocTableEntry();
        leifEntry.setStepChild(leif);
        steve.addToAssocTableList(leifEntry);

        steve = parentRepository.save(steve);

        assertEquals(4, steve.getAssocTableEntryList().size());
    }

    @Test
    public void addManyToAssocTableListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        StepChild ironman = new StepChild("Iron Man");
        StepChild thor = new StepChild("Thor");
        AssocTableEntry ironManEntry = new AssocTableEntry();
        AssocTableEntry thorEntry = new AssocTableEntry();
        ironManEntry.setStepChild(ironman);
        thorEntry.setStepChild(thor);
        List<AssocTableEntry> assocTableEntryList = new ArrayList<>();
        assocTableEntryList.add(ironManEntry);
        assocTableEntryList.add(thorEntry);

        steve.addToAssocTableList(assocTableEntryList);
        steve = parentRepository.save(steve);

        assertEquals(5, steve.getAssocTableEntryList().size());
    }

    @Test
    public void removeOneAssocTableListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        steve.removeFromAssocTableList(steve.getAssocTableEntryList().get(0));

        steve = parentRepository.save(steve);

        assertEquals(2, steve.getAssocTableEntryList().size());
        //test orphan removal
        assertEquals(2, ((List<AssocTableEntry>) assocTableEntryRepository.findAll()).size());
        //test cascade type persist
        assertEquals(3, ((List<StepChild>) stepChildRepository.findAll()).size());
    }

    @Test
    public void removeManyAssocTableListTest() {
        Parent steve = parentRepository.getParentByParentId(1);

        List<AssocTableEntry> assocTableEntryList = new ArrayList<>();
        assocTableEntryList.add(steve.getAssocTableEntryList().get(0));
        assocTableEntryList.add(steve.getAssocTableEntryList().get(1));

        steve.removeFromAssocTableList(assocTableEntryList);

        steve = parentRepository.save(steve);

        assertEquals(1, steve.getAssocTableEntryList().size());
        //test orphan removal
        assertEquals(1, ((List<AssocTableEntry>) assocTableEntryRepository.findAll()).size());
        //test cascade type persist
        assertEquals(3, ((List<StepChild>) stepChildRepository.findAll()).size());
    }
    //endregion
}
