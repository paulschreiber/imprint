//
// AdvertiserManager.java: Class file for WO Component 'AdvertiserManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AdvertiserManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected Advertiser advertiser;
    protected Advertiser advertiserItem;
    protected NSMutableArray advertiserList;
    protected WODisplayGroup displayGroup;	

    public AdvertiserManager (WOContext context) {
        super(context);
 
 		MySession.setPageTitle("Advertisers");

		editingContext = session().defaultEditingContext();

		//
		// construct the advertiserList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Advertiser", null, Helpers.advertiserOrdering);
		advertiserList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));

		//
		// construct the display group; this is used to display the list of
		// advertisers at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Advertiser") );
		displayGroup.setSortOrderings( Helpers.advertiserOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		advertiser = new Advertiser();
    }

	//
	// add a new advertiser; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAdvertiser()
    {
		if (! advertiserList.containsObject(advertiser)) {
			advertiserList.addObject( advertiser );
			editingContext.insertObject(advertiser);
			advertiser = new Advertiser();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete an advertiser; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAdvertiser()
    {
		advertiserList.removeObject( advertiserItem );
		EOEditingContext ec = advertiserItem.editingContext();
		ec.deleteObject(advertiserItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// advertiser; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAdvertiser()
    {
		advertiser = advertiserItem;
        return null;
    }

	//
	// save changes to an existing advertiser
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAdvertiser()
    {
		advertiser = new Advertiser();
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }
}
