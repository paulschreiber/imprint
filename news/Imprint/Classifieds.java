//
// Classifieds.java: Class file for WO Component 'Classifieds'
// Project Imprint
//
// Created by paul on Mon Sep 10 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Classifieds extends WOComponent {
	protected NSArray sectionList;
	protected AdSection sectionItem;
	
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    public Classifieds(WOContext context) {
        super(context);
		
		editingContext = session().defaultEditingContext();
		fetchSpec = new EOFetchSpecification("AdSection", null, Helpers.adSectionOrdering);
		sectionList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
    }

	public NSArray sectionList()
	{
		return sectionList;
	}
	
}
