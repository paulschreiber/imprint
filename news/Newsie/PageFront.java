//
// PageFront.java
// Project Newsie
//
// Created by paul on Mon Sep 10 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

//
// dummy superclass for Front1, Front2
// the methods exists so that we can return a PageFront object
// inside IssueFront or SectionFront and then set the story
// and asset lists without worrying about typing
//
public class PageFront extends WOComponent {
    public void setStoryList(NSMutableArray newStoryList) {}
    public void setAssetList(NSMutableArray newAssetList) {}
    public void setLetterList(NSMutableArray newLetterList) {}
	
    public PageFront(WOContext context) {
        super(context);
    }
}
