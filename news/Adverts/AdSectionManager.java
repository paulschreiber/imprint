//
// AdSectionManager.java: Class file for WO Component 'AdSectionManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AdSectionManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected AdSection adSection;
    protected AdSection adSectionItem;
    protected NSMutableArray adSectionList;
    protected WODisplayGroup displayGroup;

    public AdSectionManager (WOContext context) {
        super(context);

		MySession.setPageTitle("Classified Ad Sections");
 
		editingContext = session().defaultEditingContext();

		//
		// construct the adSectionList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("AdSection", null, Helpers.adSectionOrdering);
		adSectionList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));

		//
		// construct the display group; this is used to display the list of
		// classified ad sections at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "AdSection") );
		displayGroup.setSortOrderings( Helpers.adSectionOrdering );		
		displayGroup.fetch();
		
		//
		// initialize the EO objects used
		//
		adSection = new AdSection();
    }

	//
	// add a new classified ad section; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAdSection()
    {
		if (! adSectionList.containsObject(adSection)) {
			adSectionList.addObject( adSection );
			editingContext.insertObject(adSection);
			adSection = new AdSection();
		}
    
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete a classified ad section; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAdSection()
    {
		adSectionList.removeObject( adSectionItem );
		EOEditingContext ec = adSectionItem.editingContext();
		ec.deleteObject(adSectionItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// classified ad section; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAdSection()
    {
		adSection = adSectionItem;
        return null;
    }

	//
	// save changes to an existing classified ad section
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateAdSection()
    {
		adSection = new AdSection();
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }
}
