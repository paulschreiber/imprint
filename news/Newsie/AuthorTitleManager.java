//
// AuthorTitleManager.java: Class file for WO Component 'AuthorTitleManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AuthorTitleManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected AuthorTitle authorTitle;
    protected AuthorTitle authorTitleItem;
    protected NSMutableArray authorTitleList;
    protected WODisplayGroup displayGroup;	

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;

    public AuthorTitleManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Byline Titles");

		editingContext = session().defaultEditingContext();

		//
		// construct the authorTitleList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("AuthorTitle", null, Helpers.authorTitleOrdering);
		authorTitleList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));

		//
		// construct the display group; this is used to display the list of
		// byline titles at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "AuthorTitle") );
		displayGroup.setSortOrderings( Helpers.authorTitleOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		authorTitle = new AuthorTitle();
    }

	//
	// add a new author title; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAuthorTitle()
    {
		if (! authorTitleList.containsObject(authorTitle)) {
			authorTitleList.addObject( authorTitle );
			editingContext.insertObject(authorTitle);
			authorTitle = new AuthorTitle();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete a byline title; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAuthorTitle()
    {
		authorTitleList.removeObject( authorTitleItem );
		EOEditingContext ec = authorTitleItem.editingContext();
		ec.deleteObject(authorTitleItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// byline title; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAuthorTitle()
    {
		authorTitle = authorTitleItem;
		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing byline title
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAuthorTitle()
    {
		authorTitle = new AuthorTitle();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }
    
}
