//
// DirectAction.java
// Project Adverts
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

public class DirectAction extends WODirectAction {

    public DirectAction(WORequest aRequest) {
        super(aRequest);
    }

	//
	// return the main page (toolbar)
	//
    public WOActionResults defaultAction() {
        return pageWithName("Main");
    }

	//
	// classified section viewer is shared
	// see Helpers.java
	//

    public WOComponent mastheadAction() {
		return Helpers.mastheadAction(
			(MastheadReader)pageWithName("MastheadReader"),
			request(),
			session().defaultEditingContext()
		);
	}

    public WOComponent sectionAction() {
		return Helpers.sectionAction(
			(SectionReader)pageWithName("SectionReader"),
			request(),
			session().defaultEditingContext()
		);
	}

    public WOComponent classifiedAdsAction() {
		return Helpers.classifiedAdsAction(
				(ClassifiedSectionReader)pageWithName("ClassifiedSectionReader"),
				request(),
				session().defaultEditingContext()
		);
    }

    public WOComponent classifiedsAction() {
		return Helpers.classifiedsAction(
			(Classifieds)pageWithName("Classifieds"),
			request(),
			session().defaultEditingContext()
		);
	}


}
