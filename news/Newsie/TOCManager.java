//
// TOCManager.java: Class file for WO Component 'TOCManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class TOCManager extends WOComponent {
    protected IssueTOC issueTOC;
    protected IssueTOC issueTOCItem;

    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    // these are for the popups (issue, story, section)
    private EOFetchSpecification issueFetchSpec;
    protected Issue issueItem;

    private EOFetchSpecification storyFetchSpec;
    protected Story storyItem;

    private EOFetchSpecification sectionFetchSpec;
    protected Section sectionItem;

    /** @TypeInfo IssueTOC */
    protected NSMutableArray issueTOCList;

    /** @TypeInfo Issue */
    protected NSMutableArray issueList;

    /** @TypeInfo Story */
    protected NSMutableArray storyList;

    /** @TypeInfo Section */
    protected NSMutableArray sectionList;

    public TOCManager (WOContext context) {
        super(context);
 
		fetchSpec = new EOFetchSpecification("IssueTOC", null, null);
		editingContext = session().defaultEditingContext();
		issueTOCList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
		
		// get the info to populate the story popup
		storyFetchSpec = new EOFetchSpecification("Story", null, null);
		storyList = new NSMutableArray( editingContext.objectsWithFetchSpecification(storyFetchSpec));
	
		// get the info to populate the issue popup
		issueFetchSpec = new EOFetchSpecification("Issue", null, null);
		issueList = new NSMutableArray( editingContext.objectsWithFetchSpecification(issueFetchSpec));
	
		// get the info to populate the section popup
		sectionFetchSpec = new EOFetchSpecification("Section", null, null);
		sectionList = new NSMutableArray(editingContext.objectsWithFetchSpecification(sectionFetchSpec));
	
		issueItem = new Issue();
		storyItem = new Story();
		sectionItem = new Section();
		issueTOC = new IssueTOC();
    }

    public WOComponent addIssueTOC()
    {
		if (! issueTOCList.containsObject(issueTOC)) {
			issueTOCList.addObject( issueTOC );
			editingContext.insertObject(issueTOC);
			issueTOC = new IssueTOC();
		}
		
		editingContext.saveChanges();	
        return null;
    }

    public WOComponent deleteIssueTOC()
    {
		issueTOCList.removeObject( issueTOCItem );
		EOEditingContext ec = issueTOCItem.editingContext();
		ec.deleteObject(issueTOCItem);
		
		editingContext.saveChanges();
        return null;
    }

    public WOComponent editIssueTOC()
    {
		issueTOC = issueTOCItem;
		editingContext.saveChanges();

        return null;
    }

    public WOComponent updateIssueTOC()
    {
		issueTOC = new IssueTOC();
		editingContext.saveChanges();

        return null;
    }
}
