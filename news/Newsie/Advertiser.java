// Advertiser.java
// Created on Wed Aug 01 12:44:09 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Advertiser extends EOGenericRecord {

    public Advertiser() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Advertiser(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return the first name concatenated with the last name
	//
    public String fullName() {
		String firstName = (String)storedValueForKey("firstName");
		String lastName = (String)storedValueForKey("lastName");
		
		if ( firstName != null && lastName != null ) {
			return firstName + " " + lastName;
		} else if ( lastName != null ) {
			return lastName;
		} else if ( firstName != null ) {
			return firstName;
		} else {
			return "";
		}
    }


    public String company() {
        return (String)storedValueForKey("company");
    }

    public void setCompany(String value) {
        takeStoredValueForKey(value, "company");
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

    public String email() {
        return (String)storedValueForKey("email");
    }

    public void setEmail(String value) {
        takeStoredValueForKey(value, "email");
    }

    public String phone() {
        return (String)storedValueForKey("phone");
    }

    public void setPhone(String value) {
        takeStoredValueForKey(value, "phone");
    }

    public NSArray adcreativess() {
        return (NSArray)storedValueForKey("adcreativess");
    }

    public void setAdcreativess(NSMutableArray value) {
        takeStoredValueForKey(value, "adcreativess");
    }

    public void addToAdcreativess(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)adcreativess();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdcreativess(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)adcreativess();

        willChange();
        array.removeObject(object);
    }

    public NSArray adclassifiedss() {
        return (NSArray)storedValueForKey("adclassifiedss");
    }

    public void setAdclassifiedss(NSMutableArray value) {
        takeStoredValueForKey(value, "adclassifiedss");
    }

    public void addToAdclassifiedss(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)adclassifiedss();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdclassifiedss(EOEnterpriseObject object) {
        NSMutableArray array = (NSMutableArray)adclassifiedss();

        willChange();
        array.removeObject(object);
    }
}
