//
// StoryReader.java: Class file for WO Component 'StoryReader'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class StoryReader extends WOComponent {
	protected Story story;
	protected StoryAsset storyAsset;
	protected NSArray storyAssetList;
	protected boolean noStoryFound = false;
    protected AdCreative adBanner;
	
    public StoryReader(WOContext context) {
        super(context);
    }

	public void setAssetList( NSArray newAssetList )
	{
		storyAssetList = newAssetList;
	}

	public void setStory( Story aStory )
	{
		story = aStory;
	}

	public void setNoStoryFound( boolean newNoStoryFound )
	{
		noStoryFound = newNoStoryFound;
	}

	public boolean noStoryFound() {
		return noStoryFound;
	}
    public AdCreative adBanner()
    {
        return adBanner;
    }
    public void setAdBanner(AdCreative newAdBanner)
    {
        adBanner = newAdBanner;
    }

}
