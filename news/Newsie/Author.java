// Author.java
// Created on Thu Sep 06 18:08:16 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Author extends EOGenericRecord {

    public Author() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Author(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

	//
	// return HTML containing the author's name
	// with hyperlinks to his/her email address if
	// available
	//
	public String nameAndEmail() {
		String name = (String)storedValueForKey("preferredName");
		String email = (String)storedValueForKey("email");
		
		if ( name == null ) {
			name = "[no name]";
		}

		if ( (email != null) && (email.length() > 0) ) {
			return "<a href=\"mailto:" + email + "\">" + name + "</a>";
		} else {
			return name;
		}
	}
	
    public String lastName() {
        return (String)storedValueForKey("lastName");
    }

    public void setLastName(String value) {
        takeStoredValueForKey(value, "lastName");
    }

    public String firstName() {
        return (String)storedValueForKey("firstName");
    }

    public void setFirstName(String value) {
        takeStoredValueForKey(value, "firstName");
    }

    public String middleName() {
        return (String)storedValueForKey("middleName");
    }

    public void setMiddleName(String value) {
        takeStoredValueForKey(value, "middleName");
    }

    public String preferredName() {
        return (String)storedValueForKey("preferredName");
    }

    public void setPreferredName(String value) {
        takeStoredValueForKey(value, "preferredName");
    }

    public String email() {
        return (String)storedValueForKey("email");
    }

    public void setEmail(String value) {
        takeStoredValueForKey(value, "email");
    }

    public Number active() {
        return (Number)storedValueForKey("active");
    }

    public void setActive(Number value) {
        takeStoredValueForKey(value, "active");
    }

    public AuthorTitle authorTitle() {
        return (AuthorTitle)storedValueForKey("authorTitle");
    }

    public void setAuthorTitle(AuthorTitle value) {
        takeStoredValueForKey(value, "authorTitle");
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

    public NSArray assets() {
        return (NSArray)storedValueForKey("assets");
    }

    public void setAssets(NSMutableArray value) {
        takeStoredValueForKey(value, "assets");
    }

    public void addToAssets(Asset object) {
        NSMutableArray array = (NSMutableArray)assets();

        willChange();
        array.addObject(object);
    }

    public void removeFromAssets(Asset object) {
        NSMutableArray array = (NSMutableArray)assets();

        willChange();
        array.removeObject(object);
    }

    public NSArray storys2() {
        return (NSArray)storedValueForKey("storys2");
    }

    public void setStorys2(NSMutableArray value) {
        takeStoredValueForKey(value, "storys2");
    }

    public void addToStorys2(Story object) {
        NSMutableArray array = (NSMutableArray)storys2();

        willChange();
        array.addObject(object);
    }

    public void removeFromStorys2(Story object) {
        NSMutableArray array = (NSMutableArray)storys2();

        willChange();
        array.removeObject(object);
    }

    public NSArray storys3() {
        return (NSArray)storedValueForKey("storys3");
    }

    public void setStorys3(NSMutableArray value) {
        takeStoredValueForKey(value, "storys3");
    }

    public void addToStorys3(Story object) {
        NSMutableArray array = (NSMutableArray)storys3();

        willChange();
        array.addObject(object);
    }

    public void removeFromStorys3(Story object) {
        NSMutableArray array = (NSMutableArray)storys3();

        willChange();
        array.removeObject(object);
    }

    public NSArray assets1() {
        return (NSArray)storedValueForKey("assets1");
    }

    public void setAssets1(NSMutableArray value) {
        takeStoredValueForKey(value, "assets1");
    }

    public void addToAssets1(Asset object) {
        NSMutableArray array = (NSMutableArray)assets1();

        willChange();
        array.addObject(object);
    }

    public void removeFromAssets1(Asset object) {
        NSMutableArray array = (NSMutableArray)assets1();

        willChange();
        array.removeObject(object);
    }
}
