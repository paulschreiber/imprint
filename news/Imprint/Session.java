//
// Session.java
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

public class Session extends WOSession {
	protected static NSTimestamp issueDate;
	protected static String compactDate;
	protected static AdCreative adBanner;

	//
	// the page's title is stored in the session
	// - this file is shared among all three apps
	// - session variables are accessiblle from any page
	//

	protected static String basePageTitle = Helpers.PublicationName();
	protected static String pageTitle;
	protected static String pageTitleSuffix = "";

	//
	// user prefs -- batch size
	//
	protected static int batchSize = -1;

    public Session() {
        super();
		setStoresIDsInCookies( true );
		setStoresIDsInURLs( false );
	}
	
	public static void setBatchSize( int newBatchSize )
	{
		batchSize = newBatchSize;
	}
	
	public int batchSize()
	{
		return batchSize;
	}
	
	public static void setIssueDate( NSTimestamp newDate )
	{
		issueDate = newDate;
		compactDate = Helpers.CompactDateFormatter.format(issueDate);
	}
	
	public NSTimestamp issueDate()
	{
		return issueDate;
	}
	
	public String compactDate()
	{
		return compactDate;
	}

	public static void setCompactDate(String newDate) {
		compactDate = newDate;
	}

	//
	// return the base page title concatenated with newTitle
	//
	public static void setPageTitle (String newTitle) {
		if (newTitle.length() > 0) {
			pageTitle = basePageTitle + Helpers.BackendTitleSpacer() + newTitle;
			pageTitleSuffix = newTitle;
		}
	}

	public static void setBasePageTitle (String newBasePageTitle) {
		basePageTitle = newBasePageTitle;
	}

	public String pageTitle()
	{
		return pageTitle;
	}

	public AdCreative adBanner()
	{
		return adBanner;
	}
	public static void setAdBanner(AdCreative newAdBanner)
	{
		adBanner = newAdBanner;
	}

}
