//
// CampusQuestionAssistant.java: Class file for WO Component 'CampusQuestionAssistant'
// Project Newsie
//
// Created by paul on Thu Sep 13 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class CampusQuestionAssistant extends WOComponent {
    protected String name1 ="";
    protected String name2 ="";
    protected String name3 ="";
    protected String name4 ="";
    protected String name5 ="";
    protected String name6 ="";
    protected String name7 ="";
    protected String name8 ="";
    protected String name9 ="";
    protected String name10 ="";

    protected String program1 ="";
    protected String program2 ="";
    protected String program3 ="";
    protected String program4 ="";
    protected String program5 ="";
    protected String program6 ="";
    protected String program7 ="";
    protected String program8 ="";
    protected String program9 ="";
    protected String program10 ="";

    protected String quote1 ="";
    protected String quote2 ="";
    protected String quote3 ="";
    protected String quote4 ="";
    protected String quote5 ="";
    protected String quote6 ="";
    protected String quote7 ="";
    protected String quote8 ="";
    protected String quote9 ="";
    protected String quote10 ="";

    protected String fileName1 ="";
    protected String fileName2 ="";
    protected String fileName3 ="";
    protected String fileName4 ="";
    protected String fileName5 ="";
    protected String fileName6 ="";
    protected String fileName7 ="";
    protected String fileName8 ="";
    protected String fileName9 ="";
    protected String fileName10 ="";
	
    protected NSData fileData1 = new NSData();
    protected NSData fileData2 = new NSData();
    protected NSData fileData3 = new NSData();
    protected NSData fileData4 = new NSData();
    protected NSData fileData5 = new NSData();
    protected NSData fileData6 = new NSData();
    protected NSData fileData7 = new NSData();
    protected NSData fileData8 = new NSData();
    protected NSData fileData9 = new NSData();
    protected NSData fileData10 = new NSData();

	Issue issue;
	Author photographer;
	
	StoryManager referringPage;
	EOEditingContext editingContext;
	
    public CampusQuestionAssistant(WOContext context) {
        super(context);
		
		Session.setPageTitle("Campus Question Assistant");
		
		editingContext = session().defaultEditingContext();
    }

	public void setIssue (Issue newIssue) {
		issue = newIssue;
	}
	
	public void setPhotographer (Author newAuthor) {
		photographer = newAuthor;
	}
	
	public void setStoryManager(StoryManager newStoryManager)
	{
		referringPage = newStoryManager;
	}

	//
	// given an asset object, file caption and file data, set
	// the asset's attributes
	//
	public void setPhotoInfo( Asset asset, String fileName, NSData fileContents, String caption ) {
		if ( fileName.length() > 0 ) {
			//
			// save the file to disk and set its location
			//
			String aFileLocation = Helpers.handleUpload( fileName, fileContents, issue.compactDate(), Helpers.AssetUrl() );	
			asset.setLocation( aFileLocation );
	
			//
			// determine the image's size
			//
			ImageChunk ii = new ImageChunk();
			ii.setImage( fileContents );
			asset.setHeight( new Integer(ii.height()) );
			asset.setWidth( new Integer(ii.width()) );
	
			asset.setAuthor( photographer );
			asset.setIssue( issue );
			asset.setCaption( caption );

			editingContext.insertObject( asset );
		}
	}

	//
	// take all the photos, answers, people's names and
	// plug them into the template. then, generate the HTML
	//
    public WOComponent ProcessCampusQuestion()
    {
		Asset photo1 = new Asset();
		Asset photo2 = new Asset();
		Asset photo3 = new Asset();
		Asset photo4 = new Asset();
		Asset photo5 = new Asset();
		Asset photo6 = new Asset();
		Asset photo7 = new Asset();
		Asset photo8 = new Asset();
		Asset photo9 = new Asset();
		Asset photo10 = new Asset();
		
		setPhotoInfo( photo1, fileName1, fileData1, name1 );
		setPhotoInfo( photo2, fileName2, fileData2, name2 );
		setPhotoInfo( photo3, fileName3, fileData3, name3 );
		setPhotoInfo( photo4, fileName4, fileData4, name4 );
		setPhotoInfo( photo5, fileName5, fileData5, name5 );
		setPhotoInfo( photo6, fileName6, fileData6, name6 );
		setPhotoInfo( photo7, fileName7, fileData7, name7 );
		setPhotoInfo( photo8, fileName8, fileData8, name8 );
		setPhotoInfo( photo9, fileName9, fileData9, name9 );
		setPhotoInfo( photo10, fileName10, fileData10, name10 );

		//
		// create storyText for CQ
		//
		CampusQuestion cq = (CampusQuestion)pageWithName("CampusQuestion");
		
		cq.setName1( name1 );
		cq.setName2( name2 );
		cq.setName3( name3 );
		cq.setName4( name4 );
		cq.setName5( name5 );
		cq.setName6( name6 );
		cq.setName7( name7 );
		cq.setName8( name8 );
		cq.setName9( name9 );
		cq.setName10( name10 );

		cq.setProgram1( program1 );
		cq.setProgram2( program2 );
		cq.setProgram3( program3 );
		cq.setProgram4( program4 );
		cq.setProgram5( program5 );
		cq.setProgram6( program6 );
		cq.setProgram7( program7 );
		cq.setProgram8( program8 );
		cq.setProgram9( program9 );
		cq.setProgram10( program10 );

		cq.setQuote1( Helpers.escapeHTML(quote1, true) );
		cq.setQuote2( Helpers.escapeHTML(quote2, true) );
		cq.setQuote3( Helpers.escapeHTML(quote3, true) );
		cq.setQuote4( Helpers.escapeHTML(quote4, true) );
		cq.setQuote5( Helpers.escapeHTML(quote5, true) );
		cq.setQuote6( Helpers.escapeHTML(quote6, true) );
		cq.setQuote7( Helpers.escapeHTML(quote7, true) );
		cq.setQuote8( Helpers.escapeHTML(quote8, true) );
		cq.setQuote9( Helpers.escapeHTML(quote9, true) );
		cq.setQuote10( Helpers.escapeHTML(quote10, true) );

		cq.setPhoto1( photo1 );
		cq.setPhoto2( photo2 );
		cq.setPhoto3( photo3 );
		cq.setPhoto4( photo4 );
		cq.setPhoto5( photo5 );
		cq.setPhoto6( photo6 );
		cq.setPhoto7( photo7 );
		cq.setPhoto8( photo8 );
		cq.setPhoto9( photo9 );
		cq.setPhoto10( photo10 );

		WOResponse response = cq.generateResponse();
		String cqString = new String(response.content().bytes(0,response.content().length()));
		
		referringPage.story.setContents( cqString );

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

}
