//
// DirectAction.java
// Project Newsie
//
// Created by paul on Wed Sep 05 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

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
	// these story/section/issue viewers are shared
	// see Helpers.java
	//

    public WOComponent storyAction() {
		return Helpers.storyAction(
				(StoryReader)pageWithName("StoryReader"),
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

    public WOComponent issueAction() {
		return Helpers.issueAction(
			(IssueReader)pageWithName("IssueReader"),
			request(),
			session().defaultEditingContext()
		);
    }

    public WOComponent mastheadAction() {
		return Helpers.mastheadAction(
			(MastheadReader)pageWithName("MastheadReader"),
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
