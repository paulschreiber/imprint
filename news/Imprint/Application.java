//
// Application.java
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

public class Application extends WOApplication {

    public static void main(String argv[]) {
        WOApplication.main(argv, Application.class);
    }

    public Application() {
        super();

		// set this as a DirectAction app and not a ComponentAction app
		setDefaultRequestHandler(requestHandlerForKey(directActionRequestHandlerKey()));

		// initialize the sort orderings
		Helpers.setSortOrderings();

		// uncomment these to turn debugging on
//		NSLog.setAllowedDebugGroups(NSLog.DebugGroupSQLGeneration|NSLog.DebugGroupDatabaseAccess);
		NSLog.setAllowedDebugLevel(NSLog.DebugLevelDetailed);

		setSMTPHost( Helpers.SmtpHost() );
    }
    	
}
