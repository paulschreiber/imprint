//
// SectionFrontManager.java: Class file for WO Component 'SectionFrontManager'
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

public class SectionFrontManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected SectionFront sectionFront;
    protected SectionFront sectionFrontItem;
    protected NSMutableArray sectionFrontList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification issueFetchSpec;
    private EOFetchSpecification sectionFetchSpec;
    private EOFetchSpecification assetFetchSpec;

	// objects and lists for popup buttons
    protected Issue issueItem;
    protected StoryAsset storyAssetItem;
	protected Section sectionItem;
    protected NSMutableArray issueList;
    protected NSMutableArray storyAssetList;
    protected NSMutableArray sectionList;

	// fetch spec and list for the story popups
    protected NSMutableArray storyList;
    protected NSMutableArray letterList;

	// state for the pagination popup buttons at the bottom
    protected Issue selectedIssueItem;
    protected Section selectedSectionItem;

	// section front style popup
    protected String frontName;

	// warn if the form is incomplete
	protected boolean showAddWarning = false;
	protected boolean showPhotoWarning = false;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;

	// popup hiding and showing
    protected boolean showFrontStyle = true;
    protected boolean showSection = true;


    public SectionFrontManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Section Fronts");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the sectionFrontList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("SectionFront", null, Helpers.sectionFrontOrdering);
		sectionFrontList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));
		
		//
		// sectionList, used for the section popup button
		//
		sectionFetchSpec = new EOFetchSpecification("Section", null, Helpers.sectionOrdering);
		sectionList = new NSMutableArray(editingContext.objectsWithFetchSpecification(sectionFetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// section fronts at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "SectionFront") );
		displayGroup.setSortOrderings( Helpers.sectionFrontOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		storyAssetList = new NSMutableArray();		
		sectionFront = new SectionFront();
		issueItem = new Issue();
		sectionItem = new Section();
		storyList = new NSMutableArray();
		letterList = new NSMutableArray();
		storyAssetItem = null;
    }

	//
	// show the list of story front styles
	// ("images on left", "images on right")
	//
	public NSArray frontNameList()
	{
		return Helpers.frontNameList;
	}

	//
	// return true if the author, issue or front style is left blank
	//
	public boolean formIncomplete()
	{
		if ( (sectionFront.issue() != null) && (sectionItem != null) &&
				(frontName != null) && (frontName.length() > 0) ) {		
			return false;
		} else {
			return true;
		}
	}
	
	//
	// return true if the issue is left blank
	//
	public boolean formIncompleteForUpdate()
	{
		if ( sectionFront.issue() != null ) {
			return false;
		} else {
			return true;
		}
	}

	
	//
	// return true if the issue or section is left blank
	//
	public boolean formIncompleteForPhotos()
	{
		if ( (sectionFront.issue() != null) && (sectionItem != null) ) {
			return false;
		} else {
			return true;
		}
	}
	
	//
	// add a new section front; this action occurs when the
	// "Add" button is clicked; add both the database row
	// and the file itself
	//
    public WOComponent addSectionFront()
    {
		Session.setPageTitle("Section Fronts");

		if ( formIncomplete() ) {
			showAddWarning = true;
			return null;
		} else {
			showAddWarning = false;
		}
	
		if (! sectionFrontList.containsObject(sectionFront)) {

			//
			// get the current issue/section's stories to populate
			// the popup button
			//
			NSMutableArray result = new NSMutableArray();
			result = Helpers.GetSectionStories( sectionItem, sectionFront.issue().date(), editingContext );		
			storyList = (NSMutableArray) result.objectAtIndex(0);
			letterList = (NSMutableArray) result.objectAtIndex(1);

			//
			// if the story list isn't empty, start creating the section front
			//
			if ( storyList != null ) {
				PageFront myFront = (PageFront) pageWithName((String)Helpers.frontDict.objectForKey(frontName));
				
				//
				// populate the asset list; this list only holds one item
				// but i use a list to maintain compatibility with
				// IsseFronts; as well, this way it can be iterated over
				//
				NSMutableArray assetList = new NSMutableArray();
				
				if ( (storyAssetItem != null) && (storyAssetItem.asset() != null) ) {
					assetList.addObject( storyAssetItem.asset() );
				}
				
				myFront.setStoryList( storyList );
				myFront.setLetterList( letterList );
				myFront.setAssetList( assetList );
				
				//
				// create the HTML by using a WO Component
				//
				WOResponse response = myFront.generateResponse();
				String frontContent = new String(response.content().bytes(0,response.content().length()));

				sectionFront.setContent( frontContent );
				sectionFront.setSection( sectionItem );
			}


			sectionFrontList.addObject( sectionFront );
			editingContext.insertObject(sectionFront);
			sectionFront = new SectionFront();
			letterList = new NSMutableArray();
			sectionItem = new Section();
			frontName = new String();
			storyAssetItem = null;
			storyAssetList = null;
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return this;
    }

	//
	// delete a section front; this action occurs when the
	// "Delete" link is clicked; delete both the database row
	// and the file itself
	//
    public WOComponent deleteSectionFront()
    {
		Session.setPageTitle("Section Fronts");

		sectionFrontList.removeObject( sectionFrontItem );
		EOEditingContext ec = sectionFrontItem.editingContext();
		ec.deleteObject(sectionFrontItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// section front; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editSectionFront()
    {
		Session.setPageTitle("Section Fronts");

		sectionFront = sectionFrontItem;
		showUpdateButton = true;
		showAddButton = false;
		showSection = false;
		showFrontStyle = false;
        return null;
    }

	//
	// save changes to an existing section front
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateSectionFront()
    {
		Session.setPageTitle("Section Fronts");

		showPhotoWarning = false;

		if ( formIncompleteForUpdate() ) {
			showAddWarning = true;
			return null;
		} else {
			showAddWarning = false;
		}

		sectionFront = new SectionFront();
		sectionItem = new Section();
		frontName = new String();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
		showSection = true;
		showFrontStyle = true;
        return null;
    }

	//
	// get the stories for the issue selected in the popup button
	// and refresh the list at the bottom (i.e. when the user has
	// clicked "Get Stories")
	//
	public void GetIssueStories()
	{
		Helpers.GetIssueStories(selectedIssueItem, selectedSectionItem, displayGroup, null, null);
	}


	//
	// get a list of assets for the current issue and section
	// to populate the popup buttons 
	//
	public WOComponent GetCurrentSectionPhotos()
	{
		showAddWarning = false;

		if ( formIncompleteForPhotos() ) {
			showPhotoWarning = true;
			return null;
		} else {
			showPhotoWarning = false;
		}
		
		EOQualifier currentSectionQualifier;
	
		NSMutableArray args = new NSMutableArray();
		args.addObject( sectionFront.issue().date() );
		args.addObject( sectionItem );
		currentSectionQualifier = EOQualifier.qualifierWithQualifierFormat("story.issue.date = %@ and story.section = %@", args);

		assetFetchSpec = new EOFetchSpecification("StoryAsset", currentSectionQualifier, Helpers.storyAssetOrdering);
		storyAssetList = new NSMutableArray(editingContext.objectsWithFetchSpecification(assetFetchSpec));
		
		if ( storyAssetList.count() == 0 ) {
			storyAssetItem = null;
		}
		
		return null;
	}
	
}
