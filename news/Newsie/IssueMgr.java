//
// IssueMgr.java: Class file for WO Component 'IssueManager'
// Project Newsie
//
// Created by paul on Thurs Aug 23 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class IssueMgr extends WOComponent {
    protected Issue issue;
    protected Issue issueItem;

    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    /** @TypeInfo Issue */
    protected NSMutableArray issueList;

    // displaygroup stuff
    protected WODisplayGroup displayGroup;
	

    public IssueMgr (WOContext context) {
        super(context);
 
		fetchSpec = new EOFetchSpecification("Issue", null, Application.issueOrdering);
		editingContext = session().defaultEditingContext();
	
		issueList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec) );
		issue = new Issue();

		//DisplayGroup stuff
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Issue") );
		displayGroup.setSortOrderings( Application.issueOrdering );		
		displayGroup.fetch();
    }


    public WOComponent addIssue()
    {
		if (! issueList.containsObject(issue)) {
			issueList.addObject( issue );
			editingContext.insertObject(issue);
			issue = new Issue();
		}
		
		editingContext.saveChanges();	
        return null;
    }

    public WOComponent deleteIssue()
    {
		issueList.removeObject( issueItem );
		EOEditingContext ec = issueItem.editingContext();
		ec.deleteObject(issueItem);
		
		editingContext.saveChanges();
        return null;
    }

    public WOComponent editIssue()
    {
		issue = issueItem;
		editingContext.saveChanges();

        return null;
    }

    public WOComponent updateIssue()
    {
		issue = new Issue();
		editingContext.saveChanges();

        return null;
    }
    
}
