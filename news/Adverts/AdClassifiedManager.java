//
// AdClassifiedManager.java: Class file for WO Component 'AdClassifiedManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AdClassifiedManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;
    
    protected AdClassified adClassified;
    protected AdClassified adClassifiedItem;
    protected NSMutableArray adClassifiedList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification advertiserFetchSpec;
    private EOFetchSpecification issueFetchSpec;
    private EOFetchSpecification adSectionFetchSpec;

	// objects and lists for popup buttons
    protected Advertiser advertiserItem;
    protected Issue issueItem;
	protected Issue selectedIssueItem;
    protected AdSection adSectionItem;
    protected NSMutableArray advertiserList;
    protected NSMutableArray issueList;
    protected NSMutableArray adSectionList;


    public AdClassifiedManager (WOContext context) {
        super(context);
 
 		MySession.setPageTitle("Classified Ads");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the adClassifiedList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("AdClassified", null, Helpers.adClassifiedOrdering);
		adClassifiedList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// advertiserList, used for the advertiser popup button
		//
		advertiserFetchSpec = new EOFetchSpecification("Advertiser", null, Helpers.advertiserOrdering);
		advertiserList = new NSMutableArray(editingContext.objectsWithFetchSpecification(advertiserFetchSpec));

		//
		// issueList, used for the advertiser issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray(editingContext.objectsWithFetchSpecification(issueFetchSpec));
		
		//
		// adSectionList, used for the classified ad section issue popup button
		//
		adSectionFetchSpec = new EOFetchSpecification("AdSection", null, Helpers.adSectionOrdering);
		adSectionList = new NSMutableArray(editingContext.objectsWithFetchSpecification(adSectionFetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// ad creatives at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "AdClassified") );
		displayGroup.setSortOrderings( Helpers.adClassifiedOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		adClassified = new AdClassified();
		issueItem = new Issue();
		advertiserItem = new Advertiser();
		adSectionItem = new AdSection();
    }

	//
	// add a new classified ad; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAdClassified()
    {
		if (! adClassifiedList.containsObject(adClassified)) {
		
			// set the story text to an empty string if no one was entered
			// we don't want this to be null
			String adText = adClassified.ad();
			if ( adText == null ) {
				adText = "";
			}

			//
			// convert the ad text to HTML
			// add paragraph tags, escape the high ASCII characters
			//
			if ( adText.length() > 0 ) {
				// escape the entities
				adText = Helpers.escapeHTML( adText );
	
				// insert <p> tags			
				adText = Helpers.MyStringReplace(adText, "\n", "</p>\n\n<p class=\"classifiedAd\">");
				adText = "<p class=\"classifiedAd\">" + adText + "</p>";
	
				// remove empty paragraphs
				adText = Helpers.MyStringReplace(adText, "<p class=\"classifiedAd\"></p>\n", "");	
			}

			adClassified.setAd( adText );

			adClassifiedList.addObject( adClassified );
			editingContext.insertObject(adClassified);
			adClassified = new AdClassified();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete a classified ad; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAdClassified()
    {
		adClassifiedList.removeObject( adClassifiedItem );
		EOEditingContext ec = adClassifiedItem.editingContext();
		ec.deleteObject(adClassifiedItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// classified ad; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAdClassified()
    {
		adClassified = adClassifiedItem;
        return null;
    }

	//
	// save changes to an existing classified ad
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAdClassified()
    {
		adClassified = new AdClassified();
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

 
	//
	// get the ads for the issue selected in the popup button
	// and refresh the list at the bottom (i.e. when the user has
	// clicked "Get Ads")
	//
	public void GetIssueAds() {
		NSMutableArray args = new NSMutableArray();
		EOQualifier selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat("", args);
	
		if ( selectedIssueItem != null ) {
		
			args.addObject( selectedIssueItem.date() );
			selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat("issue.date = %@", args);
				
			displayGroup.setQualifier( selectedIssueQualifier );		
			displayGroup.fetch();

		} else {
			displayGroup.setQualifier( null );		
			displayGroup.fetch();
		}

	}

}
