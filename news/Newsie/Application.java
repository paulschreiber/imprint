//
// Application.java
// Project Newsie
//
// Created by paul on Fri Jul 13 2001
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

		Session.setBasePageTitle( Helpers.PublicationName() + Helpers.BackendTitleSpacer() + "Backend");

		// initialize the sort orderings
		Helpers.setSortOrderings();

		// uncomment these to turn debugging on
//		NSLog.setAllowedDebugGroups(NSLog.DebugGroupSQLGeneration|NSLog.DebugGroupDatabaseAccess);
//		NSLog.setAllowedDebugLevel(NSLog.DebugLevelDetailed);

	}


}
