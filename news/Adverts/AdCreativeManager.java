//
// AdCreativeManager.java: Class file for WO Component 'AdCreativeManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;
import java.io.*;

public class AdCreativeManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected AdCreative adCreative;
    protected AdCreative adCreativeItem;    
    protected NSMutableArray adCreativeList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification advertiserFetchSpec;
    private EOFetchSpecification issueFetchSpec;

	// objects and lists for popup buttons
    protected Advertiser advertiserItem;
    protected Issue issueItem;
    protected NSMutableArray advertiserList;
    protected NSMutableArray issueList;

    // file upload name, contents and location relative to
	// web server root
    public String aFileName;
    public NSData aFileContents = null;
	public String aFileLocation;


    public AdCreativeManager (WOContext context) {
        super(context);
 
 		MySession.setPageTitle("Banner Ad Artwork");
 
 		editingContext = session().defaultEditingContext();
	
		//
		// construct the adCreativeList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("AdCreative", null, Helpers.adCreativeOrdering);
		adCreativeList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// advertiserList, used for the advertiser popup button
		//
		advertiserFetchSpec = new EOFetchSpecification("Advertiser", null, Helpers.advertiserOrdering);
		advertiserList = new NSMutableArray( editingContext.objectsWithFetchSpecification(advertiserFetchSpec));

		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// ad creatives at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "AdCreative") );
		displayGroup.setSortOrderings( Helpers.adCreativeOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		adCreative = new AdCreative();
		advertiserItem = new Advertiser();
		issueItem = new Issue();		
    }

	//
	// add a new ad creative; this action occurs when the
	// "Add" button is clicked; add both the database row
	// and the file itself
	//
    public WOComponent addAdCreative()
    {
		if (! adCreativeList.containsObject(adCreative)) {
			//
			// save the file to disk and set its location
			//
			aFileLocation = Helpers.handleUpload( aFileName, aFileContents, adCreative.issue().compactDate(), Helpers.AdUrl() );
			adCreative.setLocation( aFileLocation );

			//
			// determine the image's size
			//
			ImageChunk ii = new ImageChunk();
			ii.setImage( aFileContents );
			adCreative.setHeight( new Integer(ii.height()) );
			adCreative.setWidth( new Integer(ii.width()) );

			adCreativeList.addObject(adCreative);
			editingContext.insertObject(adCreative);
			adCreative = new AdCreative();
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
    public WOComponent deleteAdCreative()
    {
		// delete the file from disk
		File myFile = new File ( Helpers.AssetPath() + adCreativeItem.location() );
		myFile.delete();

		// remove the row from the database
		adCreativeList.removeObject( adCreativeItem );
		EOEditingContext ec = adCreativeItem.editingContext();
		ec.deleteObject(adCreativeItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// ad creative; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAdCreative()
    {
		adCreative = adCreativeItem;

		aFileName = Helpers.AssetPath() + adCreative.location();

		try {
			aFileContents = new NSData(new FileInputStream(aFileName), 1024);
		} catch (java.io.IOException e ) {
		}

        return null;
    }

	//
	// save changes to an existing ad creative
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAdCreative()
    {
		if ( Helpers.hasUploadData(aFileContents) ) {
			// delete the old file now
			// do this first, in case the files have the same name
			File myFile = new File ( Helpers.AssetPath() + File.separator + adCreative.issue().compactDate() + File.separator + adCreative.location() );
			myFile.delete();
			
			//
			// save the file to disk and set its location
			//
			aFileLocation = Helpers.handleUpload( aFileName, aFileContents, adCreative.issue().compactDate(), Helpers.AdUrl() );
			adCreative.setLocation( aFileLocation );

			//
			// determine the image's size
			//
			ImageChunk ii = new ImageChunk();
			ii.setImage( aFileContents );
			adCreative.setHeight( new Integer(ii.height()) );
			adCreative.setWidth( new Integer(ii.width()) );

		}
		
		editingContext.saveChanges();		
		displayGroup.fetch();
		clearFileInfo();
		adCreative = new AdCreative();

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

}
