//
// AuthorManager.java: Class file for WO Component 'AuthorManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class AuthorManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;

    protected Author author;
    protected Author authorItem;
    protected NSMutableArray authorList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification authorTitleFetchSpec;

	// objects and lists for popup buttons
    protected AuthorTitle authorTitleItem;
    protected NSMutableArray authorTitleList;

	// for the query box at the bottom
	// to help with pagination
	protected String authorNameQuery;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


    public AuthorManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Authors");

		editingContext = session().defaultEditingContext();

		//
		// construct the authorList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Author", null, Helpers.authorOrdering);
		authorList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
		
		//
		// authorTitleList, used for the byline title popup button
		//
		authorTitleFetchSpec = new EOFetchSpecification("AuthorTitle", null, Helpers.authorTitleOrdering);
		authorTitleList = new NSMutableArray( editingContext.objectsWithFetchSpecification(authorTitleFetchSpec));
	
		//
		// construct the display group; this is used to display the list of
		// authors at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Author") );
		displayGroup.setSortOrderings( Helpers.authorOrdering );		
		displayGroup.fetch();

		//
		// initialize the EO objects used
		//
		author = new Author();
		authorTitleItem = new AuthorTitle();
    }

	//
	// add a new author; this action occurs when the
	// "Add" button is clicked
	//
    public WOComponent addAuthor()
    {
		if (! authorList.containsObject(author)) {
		
			// if the use forgets to fill in the preferred name
			// guess and create one (firstname space lastname)
			if ( author.preferredName() == null || author.preferredName().length() < 1 ) {
				author.setPreferredName( author.firstName() + " " + author.lastName() );
			}
		
			authorList.addObject( author );
			editingContext.insertObject(author);
			author = new Author();
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
        return null;
    }

	//
	// delete an author; this action occurs when the
	// "Delete" link is clicked
	//
    public WOComponent deleteAuthor()
    {
		authorList.removeObject( authorItem );
		EOEditingContext ec = authorItem.editingContext();
		ec.deleteObject(authorItem);
		
		editingContext.saveChanges();
		displayGroup.fetch();
        return null;
    }

	//
	// populate the form with the values of an existing 
	// author; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editAuthor()
    {
		author = authorItem;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing author
	// this action occurs when the "Update" button is clicked
	//
    public WOComponent updateAuthor()
    {
		author = new Author();
		editingContext.saveChanges();
		displayGroup.fetch();

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

	//
	// find all authors
	//
    public WOComponent findAllAuthors()
    {
		displayGroup.setQualifier( null );		
		displayGroup.setCurrentBatchIndex( 1 );
		displayGroup.fetch();
		displayGroup.updateDisplayedObjects();
		displayGroup.redisplay();

        return null;
    }


	//
	// find authors where preferredName contains the query string
	//
    public WOComponent findAuthors()
    {
	
		/*
		NSMutableArray args = new NSMutableArray();
		EOQualifier selectedAuthorsQualifier = null;
		if ( (authorNameQuery != null) && (authorNameQuery.length() > 0) ) {

			args.addObject( "*" + authorNameQuery + "*" );
			selectedAuthorsQualifier = EOQualifier.qualifierWithQualifierFormat("preferredName caseInsensitiveLike %@", args);
	
			// displayGroup.setQualifier( selectedAuthorsQualifier );		
			// displayGroup.qualifyDataSource();

		} else {
			// displayGroup.setQualifier( null );		
			// displayGroup.setCurrentBatchIndex( 1 );
			// displayGroup.fetch();
		}

		displayGroup.setCurrentBatchIndex( 1 );

		EOFetchSpecification fs = new EOFetchSpecification("Author", selectedAuthorsQualifier, null);

		NSArray authors = session().defaultEditingContext().objectsWithSpecification(fs);
		displayGroup.setAllObjects(authors);
		*/
        return null;
    }
}
