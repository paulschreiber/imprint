//
// SectionManager.java: Class file for WO Component 'SectionManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class SectionManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected Section section;
    protected Section sectionItem;
    protected NSMutableArray sectionList;
    protected WODisplayGroup displayGroup;	

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public SectionManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Sections");

		editingContext = session().defaultEditingContext();

		//
		// construct the sectionList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Section", null, Helpers.sectionOrdering);
		sectionList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));

		//
		// construct the display group; this is used to display the list of
		// sections at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Section") );
		displayGroup.setSortOrderings( Helpers.sectionOrdering );
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		section = new Section();
    }

	//
	// add a new section; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addSection()
    {
		if (! sectionList.containsObject(section)) {
			sectionList.addObject( section );
			editingContext.insertObject(section);
			section = new Section();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete a section; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteSection()
    {
		sectionList.removeObject( sectionItem );
		EOEditingContext ec = sectionItem.editingContext();
		ec.deleteObject(sectionItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// section; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editSection()
    {
		section = sectionItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing section
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateSection()
    {
		section = new Section();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }
}
