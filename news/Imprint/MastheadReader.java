//
// MastheadReader.java: Class file for WO Component 'MastheadReader'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class MastheadReader extends WOComponent {

    protected IssueMasthead issueMasthead;
	protected boolean noMastheadFound;

    public MastheadReader(WOContext context) {
        super(context);
    }

	public void setMasthead( IssueMasthead aMasthead )
	{
		issueMasthead = aMasthead;
	}

	public void setNoMastheadFound( boolean newNoMastheadFound )
	{
		noMastheadFound = newNoMastheadFound;
	}
	
	public boolean noMastheadFound()
	{
		return noMastheadFound;
	}
}
