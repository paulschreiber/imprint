//
// IssueReaderLite.java: Class file for WO Component 'IssueReaderLite'
// Project Imprint
//
// Created by paul on Thu Sep 20 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class IssueReaderLite extends WOComponent {
    protected IssueFront issueFront;
	protected NSTimestamp date;
	protected String compactDate;
    protected boolean noIssueFound;

    public IssueReaderLite(WOContext context) {
        super(context);
    }

	public void setFront( IssueFront newIssueFront )
	{
		issueFront = newIssueFront;
	}

	public void setDate (NSTimestamp newDate)
	{
		date = newDate;
		compactDate = Helpers.CompactDateFormatter.format(date);
	}

	//
	// return a href to this issue front, relative to the web server root,
	// referencing the Imprint.woa app (front end)
	//
	public String link() {
		return ( new String ( Helpers.BaseIssueUrl() + compactDate ) );
	}

    public void setNoIssueFound(boolean newNoIssueFound)
    {
        noIssueFound = newNoIssueFound;
    }

}
