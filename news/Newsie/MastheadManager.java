//
// MastheadManager.java: Class file for WO Component 'MastheadManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class MastheadManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;
	
    protected IssueMasthead issueMasthead;
    protected IssueMasthead issueMastheadItem;
    protected NSMutableArray issueMastheadList;
    protected WODisplayGroup displayGroup;	
    
    // fetch specifications for popup buttons
    private EOFetchSpecification issueFetchSpec;

	// objects and lists for popup buttons
    protected Issue issueItem;
    protected NSMutableArray issueList;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public MastheadManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Mastheads");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the issueMastheadList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("IssueMasthead", null, Helpers.mastheadOrdering);
		issueMastheadList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// mastheads at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "IssueMasthead") );
		displayGroup.setSortOrderings( Helpers.mastheadOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		issueMasthead = new IssueMasthead();
		issueItem = new Issue();
    }

	//
	// add a new masthead; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addIssueMasthead()
    {
		if (! issueMastheadList.containsObject(issueMasthead)) {
			issueMastheadList.addObject( issueMasthead );
			editingContext.insertObject(issueMasthead);
			issueMasthead = new IssueMasthead();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete a masthead; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteIssueMasthead()
    {
		issueMastheadList.removeObject( issueMastheadItem );
		EOEditingContext ec = issueMastheadItem.editingContext();
		ec.deleteObject(issueMastheadItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// masthead; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editIssueMasthead()
    {
		issueMasthead = issueMastheadItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing masthead
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateIssueMasthead()
    {
		issueMasthead = new IssueMasthead();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

 
}
