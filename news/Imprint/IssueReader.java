//
// IssueReader.java: Class file for WO Component 'IssueReader'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class IssueReader extends WOComponent {
    protected IssueFront issueFront;
	protected String topNav;
	protected boolean noIssueFound = false;

    public IssueReader(WOContext context) {
        super(context);
    }

	public void setFront( IssueFront newIssueFront )
	{
		issueFront = newIssueFront;
	}
	
	public void setTopNav( String newTopNav )
	{
		topNav = newTopNav;
	}
	
	public String topNav()
	{
		return topNav;
	}

	public void setNoIssueFound( boolean newNoIssueFound )
	{
		noIssueFound = newNoIssueFound;
	}

	public boolean noIssueFound() {
		return noIssueFound;
	}

}
