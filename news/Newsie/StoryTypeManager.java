//
// StoryTypeManager.java: Class file for WO Component 'StoryTypeManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class StoryTypeManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected StoryType storyType;
    protected StoryType storyTypeItem;
    protected NSMutableArray storyTypeList;
    protected WODisplayGroup displayGroup;	

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public StoryTypeManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Story Types");

		editingContext = session().defaultEditingContext();

		//
		// construct the storyTypeList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("StoryType", null, Helpers.storyTypeOrdering);
		storyTypeList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// story types at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );		
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "StoryType") );
		displayGroup.setSortOrderings( Helpers.storyTypeOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		storyType = new StoryType();
    }

	//
	// add a new story type; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addStoryType()
    {
		if (! storyTypeList.containsObject(storyType)) {
			storyTypeList.addObject( storyType );
			editingContext.insertObject(storyType);
			storyType = new StoryType();
		}
    
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete a story type; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteStoryType()
    {
		storyTypeList.removeObject( storyTypeItem );
		EOEditingContext ec = storyTypeItem.editingContext();
		ec.deleteObject(storyTypeItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// story type; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editStoryType()
    {
		storyType = storyTypeItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing story type
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateStoryType()
    {
		storyType = new StoryType();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }
    
}
