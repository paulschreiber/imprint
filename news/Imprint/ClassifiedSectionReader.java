//
// ClassifiedSectionReader.java: Class file for WO Component 'ClassifiedSectionReader'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class ClassifiedSectionReader extends WOComponent {
	protected NSArray adList;
	protected AdClassified adClassified;
	protected String adSection;

    public ClassifiedSectionReader(WOContext context) {
        super(context);
    }

	public void setAdList (NSArray newList) {
		adList = newList;
	}
	
	public void setAdSection (String newSection) {
		adSection = newSection;
	}
	
}
