//
// SectionReaderLite.java: Class file for WO Component 'SectionReaderLite'
// Project Imprint
//
// Created by paul on Thu Sep 20 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

//
// "lite" section fronts are dynamic and don't contain custom HTML
//

public class SectionReaderLite extends WOComponent {
	// stories and letters for the page
    protected NSMutableArray storyList;
    protected NSMutableArray letterList;	
    protected Story storyItem;
	
	// section name and date for link generation
	protected String sectionName;
	protected NSTimestamp date;
	protected String compactDate;
	
    protected String storyLink;
    protected boolean noSectionFound;

    public SectionReaderLite(WOContext context) {
        super(context);
    }

	//
	// return a href to this section front, relative to the web server root,
	// referencing the Imprint.woa app (front end)
	//
	public String link() {
		compactDate = Helpers.CompactDateFormatter.format(date);
		return ( new String ( Helpers.BaseSectionUrl() + sectionName + "&amp;date=" + compactDate ) );
	}

	public void setSectionName (String newSectionName)
	{
		sectionName = newSectionName;
	}
	
	public void setDate (NSTimestamp newDate)
	{
		date = newDate;
	}

    public NSMutableArray storyList()
    {
        return storyList;
    }
	
    public void setStoryList(NSMutableArray newStoryList)
    {
        storyList = newStoryList;
    }

    public NSMutableArray letterList()
    {
        return letterList;
    }
    public void setLetterList(NSMutableArray newLetterList)
    {
        letterList = newLetterList;
    }

    public Story storyItem()
    {
        return storyItem;
    }
    public void setStoryItem(Story newStoryItem)
    {
        storyItem = newStoryItem;
    }

    public String storyLink()
    {
        return storyLink;
    }
    public void setStoryLink(String newStoryLink)
    {
        storyLink = newStoryLink;
    }

    public boolean noSectionFound()
    {
        return noSectionFound;
    }
    public void setNoSectionFound(boolean newNoSectionFound)
    {
        noSectionFound = newNoSectionFound;
    }

}
