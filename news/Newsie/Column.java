// Column.java
// Created on Sun Aug 26 23:39:49 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Column extends EOGenericRecord {

    public Column() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Column(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public String columnName() {
        return (String)storedValueForKey("columnName");
    }

    public void setColumnName(String value) {
        takeStoredValueForKey(value, "columnName");
    }

    public Number active() {
        return (Number)storedValueForKey("active");
    }

    public void setActive(Number value) {
        takeStoredValueForKey(value, "active");
    }

    public NSArray storiess() {
        return (NSArray)storedValueForKey("storiess");
    }

    public void setStoriess(NSMutableArray value) {
        takeStoredValueForKey(value, "storiess");
    }

    public void addToStoriess(Story object) {
        NSMutableArray array = (NSMutableArray)storiess();

        willChange();
        array.addObject(object);
    }

    public void removeFromStoriess(Story object) {
        NSMutableArray array = (NSMutableArray)storiess();

        willChange();
        array.removeObject(object);
    }
}
