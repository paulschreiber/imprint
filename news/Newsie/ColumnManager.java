//
// ColumnManager.java: Class file for WO Component 'ColumnManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class ColumnManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected Column column;
    protected Column columnItem;
    protected NSMutableArray columnList;
    protected WODisplayGroup displayGroup;	

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public ColumnManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Columns");

		editingContext = session().defaultEditingContext();

		//
		// construct the columnList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Column", null, Helpers.columnOrdering);
		columnList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
		
		//
		// construct the display group; this is used to display the list of
		// columns at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Column") );
		displayGroup.setSortOrderings( Helpers.columnOrdering );
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//		
		column = new Column();
    }

	//
	// add a new column; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addColumn()
    {
		if (! columnList.containsObject(column)) {
			columnList.addObject( column );
			editingContext.insertObject(column);
			column = new Column();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete a column; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteColumn()
    {
		columnList.removeObject( columnItem );
		EOEditingContext ec = columnItem.editingContext();
		ec.deleteObject(columnItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// column; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editColumn()
    {
		column = columnItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing column
	// this action occurs when the "Update"  button is clicked
	//
    public WOComponent updateColumn()
    {
		column = new Column();
		editingContext.saveChanges();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }
}
