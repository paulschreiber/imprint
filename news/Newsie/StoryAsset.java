// StoryAsset.java
// Created on Fri Sep 14 10:16:55 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class StoryAsset extends EOGenericRecord {

    public StoryAsset() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public StoryAsset(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public Number orderInStory() {
        return (Number)storedValueForKey("orderInStory");
    }

    public void setOrderInStory(Number value) {
        takeStoredValueForKey(value, "orderInStory");
    }

    public Asset asset() {
        return (Asset)storedValueForKey("asset");
    }

    public void setAsset(Asset value) {
        takeStoredValueForKey(value, "asset");
    }

    public Story story() {
        return (Story)storedValueForKey("story");
    }

    public void setStory(Story value) {
        takeStoredValueForKey(value, "story");
    }
}
