// StoryType.java
// Created on Sun Jul 15 01:49:01 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class StoryType extends EOGenericRecord {

    public StoryType() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public StoryType(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public String storyType() {
        return (String)storedValueForKey("storyType");
    }

    public void setStoryType(String value) {
        takeStoredValueForKey(value, "storyType");
    }

    public NSArray storiess() {
        return (NSArray)storedValueForKey("storiess");
    }

    public void setStoriess(NSMutableArray value) {
        takeStoredValueForKey(value, "storiess");
    }

    public void addToStoriess(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)storiess();

        willChange();
        array.addObject(object);
    }

    public void removeFromStoriess(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)storiess();

        willChange();
        array.removeObject(object);
    }
}
