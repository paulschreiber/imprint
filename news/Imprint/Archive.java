//
// Archive.java: Class file for WO Component 'Archive'
// Project Imprint
//
// Created by paul on Tue Aug 28 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Archive extends WOComponent {
	protected IssueFront issueFront;
    protected WODisplayGroup displayGroup;	
    private EOEditingContext editingContext;

    public Archive(WOContext context) {
        super(context);
		editingContext = session().defaultEditingContext();

		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "IssueFront") );
		displayGroup.setSortOrderings( Helpers.issueFrontOrdering );		
		displayGroup.fetch();

    }

	public WODisplayGroup displayGroup() {
		return displayGroup;
	}

	public boolean existOtherBatches() {
		return ( existPreviousBatches() || existMoreBatches() );
	}

	public boolean existPreviousBatches() {
		return (displayGroup.currentBatchIndex() != 1);
	}
	
	public boolean existMoreBatches() {
		return (displayGroup.batchCount() > displayGroup.currentBatchIndex());
	}

    public int batchIndexPlusOne() {
		return displayGroup.currentBatchIndex() + 1;
    }

    public int batchIndexMinusOne() {
       return displayGroup.currentBatchIndex() - 1;
    }

}
