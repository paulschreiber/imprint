//
// StoryReaderNITF.java: Class file for WO Component 'StoryReaderNITF'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class StoryReaderNITF extends StoryReader {

    public StoryReaderNITF(WOContext context) {
        super(context);		
    }

	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		r.setHeader("text/xml", "content-type");
	}

}
