//
// SectionReader.java: Class file for WO Component 'SectionReader'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class SectionReader extends WOComponent {
	protected SectionFront sectionFront;
	protected boolean noSectionFound = false;

    public SectionReader(WOContext context) {
        super(context);
    }

	public void setSectionFront( SectionFront aSectionFront )
	{
		sectionFront = aSectionFront;
	}

	public void setNoSectionFound( boolean newNoSectionFound )
	{
		noSectionFound = newNoSectionFound;
	}

	public boolean noSectionFound() {
		return noSectionFound;
	}
}
