// AdPlacement.java
// Created on Sat Sep 15 16:43:26 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class AdPlacement extends EOGenericRecord {

    public AdPlacement() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public AdPlacement(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public Number frequency() {
        return (Number)storedValueForKey("frequency");
    }

    public void setFrequency(Number value) {
        takeStoredValueForKey(value, "frequency");
    }

    public AdCreative adCreative() {
        return (AdCreative)storedValueForKey("adCreative");
    }

    public void setAdCreative(AdCreative value) {
        takeStoredValueForKey(value, "adCreative");
    }

    public Section section() {
        return (Section)storedValueForKey("section");
    }

    public void setSection(Section value) {
        takeStoredValueForKey(value, "section");
    }
}
