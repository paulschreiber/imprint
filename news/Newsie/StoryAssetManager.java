//
// StoryAssetManager.java: Class file for WO Component 'StoryAssetManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class StoryAssetManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected StoryAsset storyAsset;
    protected StoryAsset storyAssetItem;
    protected NSMutableArray storyAssetList;
    protected WODisplayGroup displayGroup;	
    
    // fetch specifications for popup buttons
    private EOFetchSpecification issueFetchSpec;
    private EOFetchSpecification sectionFetchSpec;
    private EOFetchSpecification assetFetchSpec;
    private EOFetchSpecification storyFetchSpec;

	// objects and lists for popup buttons
    protected Issue issueItem;
    protected Issue selectedIssueItem;
    protected Section selectedSectionItem;
    protected NSMutableArray issueList;
    protected NSMutableArray sectionList;

    protected Asset assetItem;
    protected Story storyItem;
    protected NSMutableArray assetList;
    protected NSMutableArray storyList;

	// used by GetSelectedIssueStories()
	private EOQualifier currentIssueQualifier;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public StoryAssetManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Story Assets");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the storyAssetList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("StoryAsset", null, null);
		storyAssetList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));

		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));

		//
		// sectionList, used for the section popup button
		//
		sectionFetchSpec = new EOFetchSpecification("Section", null, Helpers.sectionOrdering);
		sectionList = new NSMutableArray( editingContext.objectsWithFetchSpecification(sectionFetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// story assets at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "StoryAsset") );
		displayGroup.setSortOrderings( Helpers.storyAssetOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		assetList = new NSMutableArray();
		storyList = new NSMutableArray();		
		storyItem = new Story();
		assetItem = new Asset();
		storyAsset = new StoryAsset();
    }

	//
	// add a new ad creative; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addStoryAsset()
    {
		if (! storyAssetList.containsObject(storyAsset)) {
			storyAssetList.addObject( storyAsset );
			editingContext.insertObject(storyAsset);
			storyAsset = new StoryAsset();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete a story asset; this action occurs when the
	// "Delete" link is clicked; delete both the database row
	// and the file itself
	//
    public WOComponent deleteStoryAsset()
    {
		storyAssetList.removeObject( storyAssetItem );
		EOEditingContext ec = storyAssetItem.editingContext();
		ec.deleteObject(storyAssetItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// story asset; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editStoryAsset()
    {
		issueItem = storyAssetItem.story().issue();
		GetSelectedIssueStories();
		storyAsset = storyAssetItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing story asset
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateStoryAsset()
    {
		storyAsset = new StoryAsset();
		assetList = new NSMutableArray();
		storyList = new NSMutableArray();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

	//
	// get the stories and photos for the current issue
	// and populate the popup buttons at the top
	//
	public void GetSelectedIssueStories()
	{
		NSMutableArray args = new NSMutableArray();
		args.addObject( issueItem.date() );
		currentIssueQualifier = EOQualifier.qualifierWithQualifierFormat("issue.date = %@", args);

		storyFetchSpec = new EOFetchSpecification("Story", currentIssueQualifier, Helpers.storyOrdering);
		storyList = new NSMutableArray(editingContext.objectsWithFetchSpecification(storyFetchSpec));
		
		assetFetchSpec = new EOFetchSpecification("Asset", currentIssueQualifier, Helpers.assetOrdering);
		assetList = new NSMutableArray(editingContext.objectsWithFetchSpecification(assetFetchSpec));
	}
 
	//
	// get the stories for the issue selected in the popup button
	// and refresh the list at the bottom (i.e. when the user has
	// clicked "Get Stories")
	//
	public void GetIssueStories()
	{
		Helpers.GetIssueStories(selectedIssueItem, selectedSectionItem, displayGroup, "story.", "story.");
	}

}
