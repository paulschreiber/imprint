//
// StoryManager.java: Class file for WO Component 'StoryManager'
// Project Newsie
//
// Created by paul on Sun Jul 15 2001
//

//
// NOTE: Session.setPageTitle() is called in every function on 
// StoryManager, SectionFrontManager and IssueManager because
// hitting preview will change the title to something else
// and we need to change it back
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class StoryManager extends WOComponent {
    private EOEditingContext editingContext;
    private EOFetchSpecification fetchSpec;
    
    protected Story story;
    protected Story storyItem;
    protected NSMutableArray storyList;
    protected WODisplayGroup displayGroup;	

    // fetch specifications for popup buttons
    private EOFetchSpecification authorFetchSpec;
    private EOFetchSpecification columnFetchSpec;
    private EOFetchSpecification storyTypeFetchSpec;
    private EOFetchSpecification sectionFetchSpec;
    private EOFetchSpecification issueFetchSpec;
    
	// objects and lists for popup buttons
    protected Author authorItem;
    protected Author authorItem2;
    protected Author authorItem3;
    protected Column columnItem;
    protected StoryType storyTypeItem;
    protected Section sectionItem;
    protected Issue issueItem;
    protected NSMutableArray authorList;
    protected NSMutableArray columnList;
    protected NSMutableArray storyTypeList;
    protected NSMutableArray sectionList;
    protected NSMutableArray issueList;

	// state for the pagination popup buttons at the bottom
    protected Issue selectedIssueItem;
    protected Section selectedSectionItem;

	//
	// for story assistant
	//
	
	// current story assistant name
	protected String storyAssistant;

	// for op-eds/letters to the editor
    protected String authorName;
    protected String authorYear;
	
	// for reviews
    protected String reviewLine1;
    protected String reviewLine2;
    protected String reviewLine3;
	
	// for the lede
	protected boolean detectLede = true;

	// if the column name is used for the headline
    protected boolean useColumnName;

	// what parts of the form to show and hide
    protected boolean showAuthorPopUps;
    protected boolean showStoryText;
    protected boolean showHeadline;
    protected boolean showReviewInfo;
    protected boolean showFileUpload;
	protected boolean showCQWarning;
	protected boolean showSSWarning;
	protected boolean showCartoonWarning;
	protected boolean showStoryAssistant;
	protected boolean convertHtml;
    protected boolean showEditorsResponse;
    protected boolean showLedeDetect = true;
    protected boolean showCanCon = false;

	// the editor's response to a letter
    protected String editorsResponse;
	
	// CKMS Top Ten cancon checkboxes
	protected boolean canCon1 = false;
	protected boolean canCon2 = false;
	protected boolean canCon3 = false;
	protected boolean canCon4 = false;
	protected boolean canCon5 = false;
	protected boolean canCon6 = false;
	protected boolean canCon7 = false;
	protected boolean canCon8 = false;
	protected boolean canCon9 = false;
	protected boolean canCon10 = false;

	// save button hiding and showing
    protected boolean showUpdateButton = false;
    protected boolean showAddButton = true;


	//
	// constants, retrieved from the database on initialization
	// used to set the state of the popup menus when the
	// story assistant is used
	//
	Column campusQuestionColumn;
	Column ckmsTopTenColumn;
	StoryType cartoonStoryType;
	StoryType letterStoryType;
	StoryType editorialStoryType;
	StoryType photoStoryType;
	Section forumSection;
	Section artsSection;

    // file upload name, contents and location relative to
	// web server root
    public String aFileName = "";
    public NSData aFileContents = null;
	public String aFileLocation;

	// warn if the form is incomplete
	public boolean showWarning = false;
    protected boolean showCaptionText;

    public StoryManager (WOContext context) {
        super(context);
 
		Session.setPageTitle("Stories");

		editingContext = session().defaultEditingContext();
	
		//
		// construct the storyList; this is used to prevent duplicate items
		// from being inserted into the editing context and then saved
		//
		fetchSpec = new EOFetchSpecification("Story", null, Helpers.storyOrdering);
		storyList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec));
	
		//
		// authorList, used for the author popup button
		//
		authorFetchSpec = new EOFetchSpecification("Author", null, Helpers.authorOrdering);
		authorList = new NSMutableArray( editingContext.objectsWithFetchSpecification(authorFetchSpec));
	
		//
		// columnList, used for the column popup button
		//
		columnFetchSpec = new EOFetchSpecification("Column", null, Helpers.columnOrdering);
		columnList = new NSMutableArray( editingContext.objectsWithFetchSpecification(columnFetchSpec));
		
		//
		// storyTypeList, used for the story type popup button
		//
		storyTypeFetchSpec = new EOFetchSpecification("StoryType", null, Helpers.storyTypeOrdering);
		storyTypeList = new NSMutableArray( editingContext.objectsWithFetchSpecification(storyTypeFetchSpec));
	
		//
		// sectionList, used for the section popup button
		//
		sectionFetchSpec = new EOFetchSpecification("Section", null, Helpers.sectionOrdering);
		sectionList = new NSMutableArray( editingContext.objectsWithFetchSpecification(sectionFetchSpec));
	
		//
		// issueList, used for the issue popup button
		//
		issueFetchSpec = new EOFetchSpecification("Issue", null, Helpers.issueOrdering);
		issueList = new NSMutableArray( editingContext.objectsWithFetchSpecification(issueFetchSpec));

		//
		// construct the display group; this is used to display the list of
		// stories at the bottom of the page
		//
		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Story") );
		displayGroup.setSortOrderings( Helpers.storyOrdering );		
		displayGroup.fetch();

		//
		// select the most recent Issue and use it to fetch the current issue's
		// stories for the list at the bottom
		//
		if ( issueList.count() > 0 ) {
			selectedIssueItem = (Issue)issueList.objectAtIndex(0);
			Helpers.GetIssueStories(selectedIssueItem, null, displayGroup, null, null);
		}
		
		//
		// (used for the story assistant)
		// set the default hide/show states for parts of the form
		//
		showAuthorPopUps = true;
		showStoryText = true;
		showCaptionText = false;
		showHeadline = true;
 		showReviewInfo = false;
		convertHtml = true;
		showCQWarning = false;
		showSSWarning = false;
		showCartoonWarning = false;
		showStoryAssistant = true;

		//
		// initialize the EO objects used
		//
		story = new Story();
		authorItem = new Author();
		authorItem2 = new Author();
		authorItem3 = new Author();
		columnItem = new Column();
		storyTypeItem = new StoryType();
		sectionItem = new Section();
		issueItem = new Issue();
		selectedSectionItem = new Section();		
		storyAssistant = new String();
	
		//
		// here we grab the constants that are used for setting
		// the state of popup buttons by the story assistant
		//
		EOQualifier tmpQual;
		EOFetchSpecification tmpFetchSpec;
		NSMutableArray result;
		
		//
		// forum section
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("sectionName = 'Forum'", null);
		tmpFetchSpec = new EOFetchSpecification("Section", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			forumSection = (Section) result.objectAtIndex(0);
		}
		
		//
		// arts section
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("sectionName = 'Arts'", null);
		tmpFetchSpec = new EOFetchSpecification("Section", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			artsSection = (Section) result.objectAtIndex(0);
		}

		//
		// campus question
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("columnName = 'Campus Question'", null);
		tmpFetchSpec = new EOFetchSpecification("Column", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );
		
		if ( result.count() == 1 ) {
			campusQuestionColumn = (Column) result.objectAtIndex(0);
		}
		
		//
		// ckms top ten
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("columnName = 'CKMS Top Ten'", null);
		tmpFetchSpec = new EOFetchSpecification("Column", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );
		
		if ( result.count() == 1 ) {
			ckmsTopTenColumn = (Column) result.objectAtIndex(0);
		}
				
		//
		// cartoon
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("storyType = 'Cartoon'", null);
		tmpFetchSpec = new EOFetchSpecification("StoryType", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			cartoonStoryType = (StoryType) result.objectAtIndex(0);
		}

		//
		// photo
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("storyType = 'Photo'", null);
		tmpFetchSpec = new EOFetchSpecification("StoryType", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			photoStoryType = (StoryType) result.objectAtIndex(0);
		}

		//
		// letter to the editor
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("storyType = 'Letter to the Editor'", null);
		tmpFetchSpec = new EOFetchSpecification("StoryType", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			letterStoryType = (StoryType) result.objectAtIndex(0);
		}
			
		//
		// editoral
		//
		tmpQual = EOQualifier.qualifierWithQualifierFormat("storyType = 'Editorial'", null);
		tmpFetchSpec = new EOFetchSpecification("StoryType", tmpQual, null);
		result = new NSMutableArray( editingContext.objectsWithFetchSpecification(tmpFetchSpec) );

		if ( result.count() == 1 ) {
			editorialStoryType = (StoryType) result.objectAtIndex(0);
		}
    }

	//
	// return true if the issue, headline or section is left blank
	//
	public boolean formIncomplete()
	{
		if ( (storyHasHeadlineOrColumnName() == true) && (story.section() != null) && (story.issue() != null) ) {		
			return false;
		} else {
			return true;
		}
	}

	// constant list of story assistant types
	public NSArray storyHelperList() {
		return Helpers.storyHelperList;
	}

	//
	// set the contents of the list at the bottom based on the selected
	// issue and/or section
	//
	public void GetIssueStories() {
		Helpers.GetIssueStories(selectedIssueItem, selectedSectionItem, displayGroup, null, null);
	}

	//
	// add a new story; this action occurs when the
	// "Add" button is clicked; add both the database row
	// and the file itself
	//
    public WOComponent addStory()
    {
		Session.setPageTitle("Stories");

		if ( formIncomplete() ) {
			showWarning = true;
			return null;
		} else {
			showWarning = false;
		}
	
		if (! storyList.containsObject(story)) {
		
			// set the story text to an empty string if no one was entered
			// we don't want this to be null
			String storyText = story.contents();
			if ( storyText == null ) {
				storyText = "";
			}
			
			
			//
			// fill in the headline field with the column name
			//
			if ( (useColumnName == true) && (story.column() != null) ) {
				story.setHeadline( story.column().columnName() );
			} 

			//
			// convert the story text to HTML
			// add paragraph tags, escape the high ASCII characters
			//
			if ( (storyText.length() > 0) && (convertHtml == true) ) {
				// escape the entities
				storyText = Helpers.escapeHTML( storyText );
	
				if ( (storyAssistant == null) || ((storyAssistant != null) &&
				     (!storyAssistant.equals("Table") && !storyAssistant.equals("CKMS Top Ten") )
				    ) ) {
					
					// insert <p> tags			
					storyText = Helpers.MyStringReplace(storyText, "\n", "</p>\n\n<p class=\"bodyText\">");
					
					if ( detectLede == true ) {
						storyText = "<p class=\"lede\">" + storyText + "</p>";
					} else {
						storyText = "<p class=\"bodyText\">" + storyText + "</p>";
					}
		
					// remove empty paragraphs
					storyText = Helpers.MyStringReplace(storyText, "<p class=\"bodyText\"></p>\n", "");
				}
				
				// add the editor's response
				if ( editorsResponse != null ) {
					storyText = storyText + "\n<p class=\"letterToEdResponse\"><b>" + Helpers.EditorsResponse() + "</b>"  + editorsResponse + "</p>";
				}
	
				// italicize the publication name
				storyText = Helpers.MyStringReplace(storyText, Helpers.PublicationName(), "<i>" + Helpers.PublicationName() + "</i>");
			}

			//
			// if a story assistant was chosen, use it; in most cases it's
			// just simple HTML manipulation
			//
			if ( storyAssistant != null ) {
				if ( storyAssistant.equals("Letter to the Editor") ||
					storyAssistant.equals("Editorial") ) {
					
					// don't want null appearing in the text
					if ( authorYear == null ) {
						authorYear = "";
					}
					
					if ( authorName == null ) {
						authorName = "";
					}
					
					// grab the letter/editorial text
					String originalText = storyText;
					
					// prepend "To the editor" (for letters only)
					if ( storyAssistant.equals("Letter to the Editor") ) {
						storyText = "<p class=\"letterToEdIntro\">" + Helpers.LetterToEditorIntro() + 
									"</p>\n";
					} else {
						storyText = "";
					}
								
					storyText = storyText + originalText + "\n\n<p class=\"letterToEdSignOff\">--" + 
								authorName + "\n<br>" + authorYear +
								"\n</p>\n";

					authorName = null;
					authorYear = null;

				} else if ( storyAssistant.equals("Table") ) {
					storyText = Helpers.TextToTable( storyText );

				} else if ( storyAssistant.equals("CKMS Top Ten") ) {
					// replace the second column with an X for cancon,
					// if appropriate
					NSMutableArray canConList = new NSMutableArray(
							new Object[] {
								new Boolean(canCon1), new Boolean(canCon2), new Boolean(canCon3), new Boolean(canCon4),
								new Boolean(canCon5), new Boolean(canCon6), new Boolean(canCon7), new Boolean(canCon8), 
								new Boolean(canCon9), new Boolean(canCon10)
							}
					);

					Boolean currentItem;
					for (int i=0; i < canConList.count(); i++) {
						currentItem = (Boolean) canConList.objectAtIndex(i);
						if ( currentItem.booleanValue() == true ) {
							canConList.replaceObjectAtIndex( Helpers.CanConIndicator(), i );
						} else {
							canConList.replaceObjectAtIndex( "", i );						
						}
					}
					
					//
					// remove any trailing returns from the text
					//
					while ( true ) {
						if ( storyText.lastIndexOf("\n") == storyText.length() - 1 ) {
							storyText = storyText.substring( 0, storyText.length() - 1 );
						} else {
							break;
						}
					}

					// break the table into an array of lines
					NSArray storyLineList = NSArray.componentsSeparatedByString( storyText, "\n" );
					NSMutableArray currentLine = new NSMutableArray();
					String currentLineString = null;
					
					// set story text to the first line (header)
					storyText = (String) storyLineList.objectAtIndex(0) + "\n";
					
					//
					// loop through the lines in the story, breaking each apart by tabs
					// and then inserting the true/false into the second column; then,
					// join them back by a tab and append them to the storyText
					//
					// start at line 1 so we skip the header
					//
					
					for (int i=1; i < storyLineList.count(); i++) {
						currentLineString = (String) storyLineList.objectAtIndex(i);
						
						// delete the leading tab, if it exists
						if ( currentLineString.charAt(0) == '\t' ) {
							currentLineString = currentLineString.substring(1);
						}

						// breaking into columns
						currentLine = (NSMutableArray) NSArray.componentsSeparatedByString( currentLineString, "\t" );
						
						// insert the cancon flag, if needed
						// we use (i-1) because the header line is line 0; so we need
						// to shift everything down a line
						currentLine.insertObjectAtIndex( canConList.objectAtIndex(i-1), 1 );
						
						// join into a string
						storyText = storyText + currentLine.componentsJoinedByString( "\t" ) + "\n";
					}
				
					storyText = Helpers.TextToTable( storyText, true );

				} else if ( storyAssistant.equals("Review") ) {
					storyText = "<div class=\"reviewBox\">\n" + reviewLine1 +
								"\n<br><i>" + reviewLine2 + "</i>\n" + 
								"\n<br>" + reviewLine3 + "\n</div>\n\n" + storyText;

					reviewLine1 = null;
					reviewLine2 = null;
					reviewLine3 = null;
					
				} else if ( storyAssistant.equals("Photo") || storyAssistant.equals("Cartoon") ) {
					if ( storyAssistant.equals("Cartoon") && (story.column() == null) ) {
						showCartoonWarning = true;
						return null;
					}
					
					// cartoons' headlines are the column (cartoon) name
					if ( storyAssistant.equals("Cartoon") ) {
						story.setHeadline( story.column().columnName() );
					}
				
					// set the author, and optionally a second author
					// for the photo/cartoon
					Asset asset = new Asset();
					asset.setAuthor( story.author() );
					
					if ( story.author2() != null ) {
						asset.setAuthor2( story.author2() );
					}
					
					//
					// we're pretty much duplicating AssetManager::addAsset here :)
					//
					
					asset.setIssue( story.issue() );

					//
					// save the file to disk and set its location
					//
					aFileLocation = Helpers.handleUpload( aFileName, aFileContents, story.issue().compactDate(), Helpers.AssetUrl() );
					asset.setLocation( aFileLocation );

					//
					// determine the image's size
					//
					ImageChunk ii = new ImageChunk();
					ii.setImage( aFileContents );
					asset.setHeight( new Integer(ii.height()) );
					asset.setWidth( new Integer(ii.width()) );

					String assetText = story.contents();
		
					if ( (assetText != null) && (assetText.length() > 0) ) {
						// escape the entities
						assetText = Helpers.escapeHTML( assetText );
						asset.setCaption( assetText );
					} else {
						assetText = "";
					}

					editingContext.insertObject(asset);

					storyText = "<img src=\"" + asset.location() + "\" alt=\"" + asset.caption() +
								"\" height=\"" + asset.height() + "\" width=\"" + asset.width() +
								"\">\n\n" + "<p class=\"bodyText\">\n" + asset.caption() + "</p>";
				} // photo, cartoon, etc.
				
				showCartoonWarning = false;
				
			} // assistant not null
				
			story.setContents( storyText );
				

			//
			// store the byline titles with the story
			//
			if ( story.author() != null ) {
				story.setByline( story.author().authorTitle() );
			}
	
			if ( story.author2() != null ) {
				story.setByline2( story.author2().authorTitle() );
			}
			
			if ( story.author3() != null ) {
				story.setByline3( story.author3().authorTitle() );
			}
	
			storyList.addObject( story );
			editingContext.insertObject(story);
			story = new Story();
			storyAssistant = null;
			convertHtml = true;
			editorsResponse = null;

			showAuthorPopUps = true;
			showStoryText = true;
			showCaptionText = false;
			showHeadline = true;
			showReviewInfo = false;
			showCQWarning = false;
			showSSWarning = false;
			showCartoonWarning = false;
			showFileUpload = false;
			showEditorsResponse = false;
			showLedeDetect = true;
			showCanCon = false;
			useColumnName = false;

			canCon1 = false;
			canCon2 = false;
			canCon3 = false;
			canCon4 = false;
			canCon5 = false;
			canCon6 = false;
			canCon7 = false;
			canCon8 = false;
			canCon9 = false;
			canCon10 = false;
		}
		
		editingContext.saveChanges();	
		displayGroup.fetch();
		return null;
    }

	//
	// delete a story; this action occurs when the
	// "Delete" link is clicked; delete both the database row
	// and the file itself
	//
    public WOComponent deleteStory()
    {
		Session.setPageTitle("Stories");
		
		storyList.removeObject( storyItem );
		EOEditingContext ec = storyItem.editingContext();
		ec.deleteObject(storyItem);

		// delete the associated story assets
		NSMutableArray args = new NSMutableArray();
		args.addObject( storyItem );

		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("story = %@", args);
		EOFetchSpecification fetchSpec = new EOFetchSpecification("StoryAsset", qual, null);
		NSArray storyAssetList = new NSMutableArray( editingContext.objectsWithFetchSpecification(fetchSpec) );
		int listSize = storyAssetList.count();
		StoryAsset storyAssetToDelete;
		
		for (int i=0; i < listSize; i++) {
			storyAssetToDelete = (StoryAsset) storyAssetList.objectAtIndex(i);
			ec = storyAssetToDelete.editingContext();
			ec.deleteObject(storyAssetToDelete);
		}
		
		// save changes
		editingContext.saveChanges();
		displayGroup.fetch();
		showStoryAssistant = true;
		detectLede = true;
        return null;
    }

	//
	// populate the form with the values of an existing 
	// story; this action occurs when the
	// "Edit" link is clicked
	//
    public WOComponent editStory()
    {
		Session.setPageTitle("Stories");

		story = storyItem;
		showStoryAssistant = false;
		detectLede = true;

		showUpdateButton = true;
		showAddButton = false;
        return null;
    }

	//
	// save changes to an existing story
	// this action occurs when the "Update"  button is clicked
	// we just do straight text updates, no HTML conversion,
	// no <p> tags
	//
    public WOComponent updateStory()
    {
		Session.setPageTitle("Stories");

		if ( formIncomplete() ) {
			showWarning = true;
			return null;
		} else {
			showWarning = false;
		}
	
		//
		// store the byline titles with the story
		//
		if ( story.author() != null ) 
			story.setByline( story.author().authorTitle() );

		if ( story.author2() != null ) 
			story.setByline2( story.author2().authorTitle() );

		if ( story.author3() != null ) 
			story.setByline3( story.author3().authorTitle() );
	
		editingContext.saveChanges();
		displayGroup.fetch();

		story = new Story();
		showStoryAssistant = true;
		detectLede = true;

		showUpdateButton = false;
		showAddButton = true;
        return null;
    }

	//
	// respond to clicks of the "story assistant" button
	// -- hide or show the appropriate form parts
	// -- set the state of various popup menus
	//
    public WOComponent setStoryAssistant()
    {
		showAuthorPopUps = true;
		showStoryText = true;
		showCaptionText = false;
		showHeadline = true;
		showReviewInfo = false;
		showCQWarning = false;
		showSSWarning = false;
		showCartoonWarning = false;
		showFileUpload = false;
		showEditorsResponse = false;
		showLedeDetect = true;
		showCanCon = false;

		if ( storyAssistant == null ) {
			story.setStoryType( null );
			story.setSection( null );
			story.setHeadline( null );
			story.setColumn( null );
			return null;
		}

		if ( storyAssistant.equals("Letter to the Editor") ) {
			showAuthorPopUps = false;
			showEditorsResponse = true;
			story.setStoryType( letterStoryType );
			story.setSection( forumSection );
	
		} else if ( storyAssistant.equals("CKMS Top Ten") ) {
			showLedeDetect = false;
			showCanCon = true;
			story.setHeadline( "CKMS Top Ten" );
			story.setColumn( ckmsTopTenColumn );
			story.setSection( artsSection );

		} else if ( storyAssistant.equals("Editorial") ) {
			showAuthorPopUps = false;
			story.setStoryType( editorialStoryType );
			story.setSection( forumSection );

		} else if ( storyAssistant.equals("Slide Show") ) {
			showStoryText = false;
			story.setStoryType( photoStoryType );


			if ( story.headline() == null || story.section() == null || story.issue() == null ) {
				showSSWarning = true;
			
			} else {
				showStoryText = true;
				convertHtml = false;
				
				SlideShowAssistant ssa = (SlideShowAssistant)pageWithName("SlideShowAssistant");
				ssa.setStoryManager(this);
				ssa.setIssueItem( story.issue() );
				ssa.getPhotos();

				return ssa;
			}

		} else if ( storyAssistant.equals("Campus Question") ) {
			showStoryText = false;
			story.setColumn( campusQuestionColumn );
			story.setSection( forumSection );

			if ( story.headline() == null || story.issue() == null || story.author() == null ) {
				showCQWarning = true;
			
			} else {
				showStoryText = true;
				convertHtml = false;
				
				CampusQuestionAssistant cqa = (CampusQuestionAssistant)pageWithName("CampusQuestionAssistant");
				cqa.setStoryManager(this);
				
				cqa.setIssue( story.issue() );
				cqa.setPhotographer( story.author() );

				return cqa;
			}

		} else if ( storyAssistant.equals("Review") ) {
			showReviewInfo = true;
			
		} else if ( storyAssistant.equals("Cartoon") ) {
			showStoryText = false;
			showCaptionText = true;
			showHeadline = false;
			showFileUpload = true;
			story.setStoryType( cartoonStoryType );
			
			if ( story.column() == null ) {
				showCartoonWarning = true;
			}

		} else if ( storyAssistant.equals("Photo") ) {
			showFileUpload = true;
			showCaptionText = true;
			showStoryText = false;
			story.setStoryType( photoStoryType );

		} else if ( storyAssistant.equals("Table") ) {
			// nothing
		}
		
        return null;
    }

    public boolean showCaptionText()
    {
        return showCaptionText;
    }
    public void setShowCaptionText(boolean newShowCaptionText)
    {
        showCaptionText = newShowCaptionText;
    }

	//
	// return true if the headline field is not blank, or a
	// column is chosen and the "use column name" box is checked
	//
	public boolean storyHasHeadlineOrColumnName()
	{
		if ( story.headline() != null ) {
			return true;
		}
		
		if ( (useColumnName == true) && (story.column() != null) ) {
			return true;
		}
		
		return false;
	}

}
