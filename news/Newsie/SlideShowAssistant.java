//
// SlideShowAssistant.java: Class file for WO Component 'SlideShowAssistant'
// Project Newsie
//
// Created by paul on Wed Nov 14 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class SlideShowAssistant extends WOComponent {
    // fetch specs, etc.
	protected EOQualifier photoQualifier;
    protected EOFetchSpecification assetFetchSpec;
    protected NSMutableArray assetList;
	protected Asset assetItem;

	// for the selected photos
    protected NSMutableArray checkedAssetList;

	// for the getPhotos() query
	protected Issue issueItem;

	StoryManager referringPage;
	EOEditingContext editingContext;

    public SlideShowAssistant(WOContext context) {
        super(context);

		Session.setPageTitle("Slide Show Assistant");
		editingContext = session().defaultEditingContext();
		checkedAssetList = new NSMutableArray();
    }

	public void setStoryManager(StoryManager newStoryManager)
	{
		referringPage = newStoryManager;
	}
	
	public void setIssueItem(Issue newIssueItem)
	{
		issueItem = newIssueItem;
	}

    public WOComponent ProcessSlideShow()
    {
		//
		// create storyText for SS
		//
		SlideShow ss = (SlideShow)pageWithName("SlideShow");

		ss.setPhotoList( checkedAssetList );
		Asset firstPhoto = (Asset) checkedAssetList.objectAtIndex(0);
		ss.setFirstCaption( firstPhoto.caption() );
		ss.setFirstSrc( firstPhoto.location() );
		ss.setFirstHeight( firstPhoto.height().toString() );
		ss.setFirstWidth( firstPhoto.width().toString() );
		ss.setFirstCredit( firstPhoto.AuthorNames() );
		
		if ( firstPhoto.hideCaption().intValue() != 0 ) {
			ss.setFirstCaptionHidden( true );
		} else {
			ss.setFirstCaptionHidden( false );
		}

		ss.setPhotoList( checkedAssetList );

		// generate the HTML
		WOResponse response = ss.generateResponse();
		String ssString = new String(response.content().bytes(0,response.content().length()));
		
		referringPage.story.setContents( ssString );

		// now save the story
		// don't want the user to have to hit add
		referringPage.storyList.addObject( referringPage.story );
		editingContext.insertObject( referringPage.story );
		editingContext.saveChanges();	

		// reset the info
		referringPage.story = new Story();
		referringPage.storyAssistant = null;
		referringPage.convertHtml = true;
		referringPage.displayGroup.fetch();

		Session.setPageTitle("Stories");
		
        return referringPage;
    }

	public void getPhotos()
	{
		NSMutableArray args = new NSMutableArray();
		args.addObject( issueItem );
		photoQualifier = EOQualifier.qualifierWithQualifierFormat("issue = %@", args);

		assetFetchSpec = new EOFetchSpecification("Asset", photoQualifier, Helpers.assetOrdering);
		assetList = new NSMutableArray(editingContext.objectsWithFetchSpecification(assetFetchSpec));
		
		if ( assetList.count() == 0 ) {
			assetItem = null;
		}
	}
}
