//
// IssueMgr.java: Class file for WO Component 'IssueManager'
// Project Newsie
//
// Created by paul on Thurs Aug 23 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class IssueManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected Issue issue;
    protected Issue issueItem;
    protected NSMutableArray issueList;
    protected WODisplayGroup displayGroup;	
	
	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;

    public IssueManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Issues");

		editingContext = session().defaultEditingContext();

		//
		// construct the issueList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec) );
		
		//
		// construct the display group; this is used to display the list of
		// issues at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Issue") );
		displayGroup.setSortOrderings( Helpers.issueOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		issue = new Issue();
    }


	//
	// add a new issue; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addIssue()
    {
		if (! issueList.containsObject(issue)) {
			issueList.addObject( issue );
			editingContext.insertObject(issue);
			issue = new Issue();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete an issue; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteIssue()
    {
		issueList.removeObject( issueItem );
		EOEditingContext ec = issueItem.editingContext();
		ec.deleteObject(issueItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// issue; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editIssue()
    {
		issue = issueItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing issue
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateIssue()
    {
		issue = new Issue();
		editingContext.saveChanges();
		displayGroup.fetch();


		showUpdateButton = false;
		showAddButton = true;
        return null;
    }
    
}
