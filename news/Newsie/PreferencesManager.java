//
// PreferencesManager.java: Class file for WO Component 'PreferencesManager'
// Project Newsie
//
// Created by paul on Mon Oct 01 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;

public class PreferencesManager extends WOComponent {
	protected String batchSize;

	public String batchSize()
	{
		return batchSize;
	}

    public PreferencesManager(WOContext context) {
        super(context);
 
		Session.setPageTitle("Preferences");
		batchSize = Integer.toString( Helpers.BatchSize() );
    }

	//
	// set the session's batch size
	//
    public WOComponent saveChanges()
    {
		Session.setBatchSize( Integer.parseInt( batchSize ) );

		return null;
    }

}
