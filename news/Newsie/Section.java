// Section.java
// Created on Sat Sep 15 16:10:45 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Section extends EOGenericRecord {

    public Section() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Section(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
        super(context, classDesc, gid);
    }

    // If you add instance variables to store property values you
    // should add empty implementions of the Serialization methods
    // to avoid unnecessary overhead (the properties will be
    // serialized for you in the superclass).
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    }
*/

    public String sectionName() {
        return (String)storedValueForKey("sectionName");
    }

    public void setSectionName(String value) {
        takeStoredValueForKey(value, "sectionName");
    }

    public NSArray storys() {
        return (NSArray)storedValueForKey("storys");
    }

    public void setStorys(NSMutableArray value) {
        takeStoredValueForKey(value, "storys");
    }

    public void addToStorys(Story object) {
        NSMutableArray array = (NSMutableArray)storys();

        willChange();
        array.addObject(object);
    }

    public void removeFromStorys(Story object) {
        NSMutableArray array = (NSMutableArray)storys();

        willChange();
        array.removeObject(object);
    }

    public NSArray adplacements() {
        return (NSArray)storedValueForKey("adplacements");
    }

    public void setAdplacements(NSMutableArray value) {
        takeStoredValueForKey(value, "adplacements");
    }

    public void addToAdplacements(AdPlacement object) {
        NSMutableArray array = (NSMutableArray)adplacements();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdplacements(AdPlacement object) {
        NSMutableArray array = (NSMutableArray)adplacements();

        willChange();
        array.removeObject(object);
    }
}
