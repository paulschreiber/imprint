//
// IssueFrontManager.java: Class file for WO Component 'IssueFrontManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

//
// NOTE: Session.setPageTitle() is called in every function on 
// StoryManager, SectionFrontManager and IssueManager because
// hitting preview will change the title to something else
// and we need to change it back
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;
import java.io.*;

public class IssueFrontManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected IssueFront issueFront;
    protected IssueFront issueFrontItem;
    protected NSMutableArray issueFrontList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification issueFetchSpec;
    private EOFetchSpecification storyFetchSpec;
    private EOFetchSpecification assetFetchSpec;

	// objects and lists for popup buttons
    protected Issue issueItem;
    protected Story storyItem1;
    protected Story storyItem2;
    protected Story storyItem3;
    protected Story storyItem4;
    protected Story storyItem5;
    protected Asset assetItem1;
    protected Asset assetItem2;
    protected NSMutableArray issueList;
    protected NSMutableArray storyList;
    protected NSMutableArray assetList;

	// used by GetCurrentIssueStories
	private EOQualifier currentIssueQualifier;
	
	//
	// lists of stories and assets which we will attach
	// to the FrontNN component and use to generate HTML
	//
	protected NSMutableArray issueFrontStoryList;
	protected NSMutableArray issueFrontAssetList;
	
	// section front style popup
    protected String frontName;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public IssueFrontManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Issue Fronts");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the issueFrontList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("IssueFront", null, Helpers.issueFrontOrdering);
		issueFrontList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));

		//
		// construct the display group; this is used to display the list of
		// issue fronts at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "IssueFront") );
		displayGroup.setSortOrderings( Helpers.issueFrontOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		issueFront = new IssueFront();
		issueItem = new Issue();
		storyItem1 = new Story();
		storyItem2 = new Story();
		storyItem3 = new Story();
		storyItem4 = new Story();
		storyItem5 = new Story();
		storyList = new NSMutableArray();
		assetList = new NSMutableArray();
		issueFrontStoryList = new NSMutableArray();
		issueFrontAssetList = new NSMutableArray();
    }

	//
	// show the list of story front styles
	// ("images on left", "images on right")
	//
	public NSArray frontNameList() {
		return Helpers.frontNameList;
	}
	
	//
	// show the story popup buttons if the issueFront has no content 
	// (i.e. the user has *not* clicked "edit") or if the storyList popup has
	// been populated (i.e. the user has clicked "Get Stories")
	//
	public boolean showPopUps() {	
		return ( (storyList.count() > 0) ||
					((issueFront != null) &&
					(issueFront.content() != null) &&
					(issueFront.content().length() == 0)) );
	}
	
	//
	// show the "Add" and "Update" buttons if the issueFront has content 
	// (i.e. the user has clicked "edit") or if the storyList popup has
	// been populated (i.e. the user has clicked "Get Stories")
	//
	public boolean showButtons() {
		return ( ((issueFront != null) &&
					(issueFront.content() != null) &&
					(issueFront.content().length() > 0)) || (storyList.count() > 0) );
	}

	//
	// get a list of stories and a list of assets for the current issue
	// to populate the popup buttons 
	//
	public void GetCurrentIssueStories()
	{
		NSMutableArray args = new NSMutableArray();
		args.addObject( issueFront.issue().date() );
		currentIssueQualifier = EOQualifier.qualifierWithQualifierFormat("issue.date = %@", args);

		storyFetchSpec = new EOFetchSpecification("Story", currentIssueQualifier, Helpers.storyOrdering);
		storyList = new NSMutableArray(editingContext.objectsWithFetchSpecification(storyFetchSpec));
		
		assetFetchSpec = new EOFetchSpecification("Asset", currentIssueQualifier, Helpers.assetOrdering);
		assetList = new NSMutableArray(editingContext.objectsWithFetchSpecification(assetFetchSpec));
	}
 	
	//
	// add a new issue front; this action occurs when the
	// "Add" button is clicked; add both the database row
	// and the file itself
	//
    public WOComponent addIssueFront()
    {
		Session.setPageTitle("Issue Fronts");

		if (! issueFrontList.containsObject(issueFront)) {

			//
			// only create the front if they've chosen at least one story
			//
			if ( storyItem1 != null ) {
					
				PageFront myFront = (PageFront)pageWithName((String)Helpers.frontDict.objectForKey(frontName));
				
				//
				// populate the story list
				//
				if ( storyItem1 != null )
					issueFrontStoryList.addObject( storyItem1 );
				
				if ( storyItem2 != null )
					issueFrontStoryList.addObject( storyItem2 );
				
				if ( storyItem3 != null )
					issueFrontStoryList.addObject( storyItem3 );
				
				if ( storyItem4 != null )
					issueFrontStoryList.addObject( storyItem4 );
				
				if ( storyItem5 != null )
					issueFrontStoryList.addObject( storyItem5 );
					
				myFront.setStoryList( issueFrontStoryList );
								
				//
				// populate the asset list
				//
				if ( assetItem1 != null )
					issueFrontAssetList.addObject( assetItem1 );
					
				if ( assetItem2 != null )
					issueFrontAssetList.addObject( assetItem2 );
				myFront.setAssetList( issueFrontAssetList );

				//
				// create the HTML by using a WO Component
				//
				WOResponse response = myFront.generateResponse();
				String frontContent = new String(response.content().bytes(0,response.content().length()));
	
				issueFront.setContent( frontContent );
			}

			issueFrontList.addObject( issueFront );
			editingContext.insertObject(issueFront);
			issueFront = new IssueFront();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return this;
    }

	//
	// delete an issue front; this action occurs when the
	// "Delete" link is clicked; delete both the database row
	// and the file itself
	//
    public WOComponent deleteIssueFront()
    {
		Session.setPageTitle("Issue Fronts");

		issueFrontList.removeObject( issueFrontItem );
		EOEditingContext ec = issueFrontItem.editingContext();
		ec.deleteObject(issueFrontItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// ad creative; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editIssueFront()
    {
		Session.setPageTitle("Issue Fronts");

		issueFront = issueFrontItem;
		storyList = new NSMutableArray();
		assetList = new NSMutableArray();

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing issue front
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateIssueFront()
    {
		Session.setPageTitle("Issue Fronts");

		issueFront = new IssueFront();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

}
