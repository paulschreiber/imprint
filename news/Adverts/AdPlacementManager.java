//
// AdPlacementManager.java: Class file for WO Component 'AdPlacementManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AdPlacementManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;
    
    protected AdPlacement adPlacement;
    protected AdPlacement adPlacementItem;
    protected NSMutableArray adPlacementList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification adCreativeFetchSpec;
    private EOFetchSpecification sectionFetchSpec;

	// objects and lists for popup buttons
    protected AdCreative adCreativeItem;
    protected Section sectionItem;
    protected NSMutableArray adCreativeList;
    protected NSMutableArray sectionList;

    public AdPlacementManager (WOContext context) {
        super(context);
 
 		MySession.setPageTitle("Banner Ad Placement");
		
		editingContext = session().defaultEditingContext();
	
		//
		// construct the adPlacementList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("AdPlacement", null, Helpers.adPlacementOrdering);
		adPlacementList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// adCreativeList, used for the ad creative popup button
		//
		adCreativeFetchSpec = new EOFetchSpecification("AdCreative", null, Helpers.adCreativeOrdering);
		adCreativeList = new NSMutableArray(editingContext.objectsWithFetchSpecification(adCreativeFetchSpec));

		//
		// sectionList, used for the section popup button
		//
		sectionFetchSpec = new EOFetchSpecification("Section", null, Helpers.sectionOrdering);
		sectionList = new NSMutableArray(editingContext.objectsWithFetchSpecification(sectionFetchSpec));

		//
		// construct the display group; this is used to display the list of
		// ad creatives at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "AdPlacement") );
		displayGroup.setSortOrderings( Helpers.adPlacementOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		adPlacement = new AdPlacement();
		adCreativeItem = new AdCreative();
		sectionItem = new Section();
    }

	//
	// add a new ad placement; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAdPlacement()
    {
		if (! adPlacementList.containsObject(adPlacement)) {
			adPlacementList.addObject( adPlacement );
			editingContext.insertObject(adPlacement);
			adPlacement = new AdPlacement();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete an ad placement; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAdPlacement()
    {
		adPlacementList.removeObject( adPlacementItem );
		EOEditingContext ec = adPlacementItem.editingContext();
		ec.deleteObject(adPlacementItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// ad placement; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAdPlacement()
    {
		adPlacement = adPlacementItem;
        return null;
    }

	//
	// save changes to an existing ad placement
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAdPlacement()
    {
		adPlacement = new AdPlacement();
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

 
}
