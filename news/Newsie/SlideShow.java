//
// SlideShow.java: Class file for WO Component 'SlideShow'
// Project Newsie
//
// Created by paul on Wed Nov 14 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class SlideShow extends WOComponent {
    protected String firstCaption;
    protected String firstSrc;
    protected String firstHeight;
    protected String firstWidth;
    protected String firstCredit;

    protected NSMutableArray photoList;
    protected Asset photoItem;
    protected boolean firstCaptionHidden;

    public SlideShow(WOContext context) {
        super(context);
    }

    public String firstCaption()
    {
        return firstCaption;
    }
    public void setFirstCaption(String newFirstCaption)
    {
        firstCaption = newFirstCaption;
    }

    public String firstSrc()
    {
        return firstSrc;
    }
    public void setFirstSrc(String newFirstSrc)
    {
        firstSrc = newFirstSrc;
    }

    public String firstHeight()
    {
        return firstHeight;
    }
    public void setFirstHeight(String newFirstHeight)
    {
        firstHeight = newFirstHeight;
    }

    public String firstWidth()
    {
        return firstWidth;
    }
    public void setFirstWidth(String newFirstWidth)
    {
        firstWidth = newFirstWidth;
    }

    public String firstCredit()
    {
        return firstCredit;
    }
    public void setFirstCredit(String newFirstCredit)
    {
        firstCredit = newFirstCredit;
    }

    public NSMutableArray photoList()
    {
        return photoList;
    }
    public void setPhotoList(NSMutableArray newPhotoList)
    {
        photoList = newPhotoList;
    }

    public Asset photoItem()
    {
        return photoItem;
    }
    public void setPhotoItem(Asset newPhotoItem)
    {
        photoItem = newPhotoItem;
    }

    public boolean firstCaptionHidden()
    {
        return firstCaptionHidden;
    }
    public void setFirstCaptionHidden(boolean newFirstCaptionHidden)
    {
        firstCaptionHidden = newFirstCaptionHidden;
    }

}
