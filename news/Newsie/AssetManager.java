//
// AssetManager.java: Class file for WO Component 'AssetManager'
// Project Newsie
//
// Created by paul on Wed Aug 8 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;
import java.io.*;

public class AssetManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;
    
    protected Asset asset;
    protected Asset assetItem;
    protected NSMutableArray assetList;
    protected WODisplayGroup displayGroup;	
    
    // fetch specifications for popup buttons
    private EOFetchSpecification authorFetchSpec;
    private EOFetchSpecification issueFetchSpec;
    private EOFetchSpecification sectionFetchSpec;

	// objects and lists for popup buttons
    protected Author authorItem;
    protected Author authorItem2;
    protected Issue issueItem;
    protected NSMutableArray authorList;
    protected NSMutableArray issueList;
    protected NSMutableArray sectionList;
    protected Issue selectedIssueItem;

    // file upload name, contents and location relative to
	// web server root
    public String aFileName = "";
    public NSData aFileContents = null;
	public String aFileLocation;
	
	// warn if the form is incomplete
	public boolean showWarning = false;

	// warn if upload fails
	public boolean showUploadFailed = false;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;

    public AssetManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Assets");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the assetList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Asset", null, Helpers.assetOrdering);
		assetList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// authorList, used for the author popup button
		//
		authorFetchSpec = new EOFetchSpecification("Author", null, Helpers.authorOrdering);
		authorList = new NSMutableArray(editingContext.objectsWithFetchSpecification(authorFetchSpec));
		
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
		// assets at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Asset") );
		displayGroup.setSortOrderings( Helpers.assetOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		asset = new Asset();
		authorItem = new Author();
		authorItem2 = new Author();
		issueItem = new Issue();		
    }


	//
	// return true if the author, issue or caption is left blank
	//
	public boolean formIncomplete()
	{
		if ( (asset.author() != null) && (asset.issue() != null) &&
				(asset.caption() != null) && (asset.caption().length() > 0) ) {
		
			return false;
		} else {
			return true;
		}
	}

	//
	// add a new asset; this action occurs when the
	// "Add" button is clicked; add both the database row
	// and the file itself
	//
    public WOComponent addAsset()
    {
		showUploadFailed = false;
	
		if ( formIncomplete() ) {
			showWarning = true;
			return null;
		} else {
			showWarning = false;
		}
	
		if (! assetList.containsObject(asset)) {
			//
			// save the file to disk and set its location
			//
			if ( aFileContents != null ) {
				aFileLocation = Helpers.handleUpload( aFileName, aFileContents, asset.issue().compactDate(), Helpers.AssetUrl() );
			
				//
				// if upload fails, display a warning
				//
				if ( aFileLocation.equals("FAILED") ) {
					showUploadFailed = true;
					return null;
				}
				
				asset.setLocation( aFileLocation );
	
				//
				// determine the image's size
				//
				ImageChunk ii = new ImageChunk();
				ii.setImage( aFileContents );
				asset.setHeight( new Integer(ii.height()) );
				asset.setWidth( new Integer(ii.width()) );
			}
			
			// set the caption
			String assetText = asset.caption();
			
			if ( assetText.length() > 0 ) {
				// escape the entities
				assetText = Helpers.escapeHTML( assetText );
				asset.setCaption( assetText );
			}

			assetList.addObject( asset );
			editingContext.insertObject(asset);
			asset = new Asset();
		}
		
		editingContext.saveChanges();
		displayGroup.fetch();
		clearFileInfo();	
		return null;
    }

	//
	// delete an ad creative; this action occurs when the
	// "Delete" link is clicked; delete both the database row
	// and the file itself
	//
    public WOComponent deleteAsset()
    {
		// delete the actual file
		File myFile = new File ( Helpers.AssetPath() + assetItem.location() );
		myFile.delete();
		
		// delete the asset
		assetList.removeObject( assetItem );
		EOEditingContext ec = assetItem.editingContext();
		ec.deleteObject(assetItem);
		
		// delete the associated story assets
		NSMutableArray args = new NSMutableArray();
		args.addObject( assetItem );

		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("asset = %@", args);
		EOFetchSpecification fetchSpec = new EOFetchSpecification("StoryAsset", qual, null);
		NSArray storyAssetList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec) );
		int listSize = storyAssetList.count();
		StoryAsset storyAssetToDelete;
		
		for (int i=0; i < listSize; i++) {
			storyAssetToDelete = (StoryAsset) storyAssetList.objectAtIndex(i);
			ec = storyAssetToDelete.editingContext();
			ec.deleteObject(storyAssetToDelete);
		}
		
		// save changes
		editingContext.saveChanges();
		displayGroup.fetch();
		
        return null;
    }

	//
	// populate the form with the values of an existing 
	// ad creative; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAsset()
    {
		asset = assetItem;
		aFileName = Helpers.AssetPath() + asset.location();
		
		try {
			aFileContents = new NSData(new FileInputStream(aFileName), 1024);
		} catch (java.io.IOException e ) {
		}
				
		showUpdateButton = true;
		showAddButton = false;
		return null;
    }

	//
	// save changes to an existing asset
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAsset()
    {
		if ( formIncomplete() ) {
			showWarning = true;
			return null;
		} else {
			showWarning = false;
		}

		if ( Helpers.hasUploadData(aFileContents) ) {
			// delete the old file now
			// do this first, in case the files have the same name
			File myFile = new File ( Helpers.AssetPath() + asset.location() );
			myFile.delete();

			aFileLocation = Helpers.handleUpload( aFileName, aFileContents, asset.issue().compactDate(), Helpers.AssetUrl() );
			
			//
			// determine the image's size
			//
			ImageChunk ii = new ImageChunk();
			ii.setImage( aFileContents );
			asset.setHeight( new Integer(ii.height()) );
			asset.setWidth( new Integer(ii.width()) );

			asset.setLocation( aFileLocation );
		}
		
		editingContext.saveChanges();		
		displayGroup.fetch();
		clearFileInfo();

		asset = new Asset();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

	//
	// clear the image file from memory
	// do this after changes; needed for page display
	//
	public void clearFileInfo() {
		aFileName = "";
		aFileContents = null;
	}

	//
	// return true if there is an image file to display
	//
    public boolean hasUploadData() {
        return Helpers.hasUploadData( aFileContents );
    }

	//
	// get the stories for the issue selected in the popup button
	// and refresh the list at the bottom (i.e. when the user has
	// clicked "Get Stories")
	//
	public void GetIssueStories()
	{
		Helpers.GetIssueStories(selectedIssueItem, null, displayGroup, null, null);
	}


} //end
