// AuthorTitle.java
// Created on Mon Jul 16 12:56:07 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class AuthorTitle extends EOGenericRecord {

    public AuthorTitle() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public AuthorTitle(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return the byline title, unless it is "n/a", 
	// then return blank instead
	//
	public String titleIfValidTitle() {
		String title = (String)storedValueForKey("title");
		
		if ( title.equals( Helpers.BlankByline() ) ) {
			return "";
		}
		
		return title;
	}

    public String title() {
        return (String)storedValueForKey("title");
    }

    public void setTitle(String value) {
        takeStoredValueForKey(value, "title");
    }

    public NSArray author() {
        return (NSArray)storedValueForKey("author");
    }

    public void setAuthor(NSMutableArray value) {
        takeStoredValueForKey(value, "author");
    }

    public void addToAuthor(Author object) {
        NSMutableArray array = (NSMutableArray)author();

        willChange();
        array.addObject(object);
    }

    public void removeFromAuthor(Author object) {
        NSMutableArray array = (NSMutableArray)author();

        willChange();
        array.removeObject(object);
    }
}
