//
// Front1.java: Class file for WO Component 'SectionFront1'
// Project Newsie
//
// Created by paul on Thu Aug 30 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Front1 extends PageFront {
	// stories, letters and assets for the page
    protected NSMutableArray storyList;
    protected NSMutableArray assetList;	
    protected NSMutableArray letterList;	
    protected Story storyItem;
    protected Asset assetItem;
	
    protected String storyLink;

    public Front1(WOContext context) {
        super(context);
    }

    public NSMutableArray storyList()
    {
        return storyList;
    }
	
    public void setStoryList(NSMutableArray newStoryList)
    {
        storyList = newStoryList;
    }

    public NSMutableArray assetList()
    {
        return assetList;
    }
    public void setAssetList(NSMutableArray newAssetList)
    {
        assetList = newAssetList;
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

}
