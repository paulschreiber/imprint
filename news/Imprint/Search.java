//
// Search.java: Class file for WO Component 'Search'
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Search extends WOComponent {
    protected String headline;
    protected String body;
    protected String author;
	protected Story story;

    protected WODisplayGroup displayGroup;	
    private EOEditingContext editingContext;
	
    protected String startMonth;
    protected String endMonth;
    protected String startDay;
    protected String endDay;
    protected String startYear;
    protected String endYear;

	protected int previousBatch;
	protected int nextBatch;

	public void setPreviousBatch(int newPreviousBatch) {
		previousBatch = newPreviousBatch;
	}
	
	public void setNextBatch(int newNextBatch) {
		nextBatch = newNextBatch;
	}
	
	public void setDisplayGroup(WODisplayGroup newDisplayGroup) {
		displayGroup = newDisplayGroup;
	}
	
	public NSArray monthList()
	{
		return Helpers.monthList;
	}
	
	public NSArray dayList()
	{
		return Helpers.dayList;
	}
	
	public NSArray yearList()
	{
		return Helpers.yearList();
	}
	
    public Search(WOContext context) {
        super(context);

		editingContext = session().defaultEditingContext();

		displayGroup = new WODisplayGroup();
		displayGroup.setNumberOfObjectsPerBatch( Helpers.BatchSize() );
		displayGroup.setDataSource( new EODatabaseDataSource(editingContext, "Story") );
		displayGroup.setSortOrderings( Helpers.storyOrdering );		
    }

	public WODisplayGroup displayGroup() {
		return displayGroup;
	}

	public String headline()
	{
		return headline;
	}
	
	public void setHeadline(String newHeadline)
	{
		headline = newHeadline;
	}

	public String body()
	{
		return body;
	}
	
	public void setBody(String newBody)
	{
		body = newBody;
	}

	public String author()
	{
		return author;
	}
	
	public void setAuthor(String newAuthor)
	{
		author = newAuthor;
	}

    public String startMonth()
    {
        return startMonth;
    }
    public void setStartMonth(String newStartMonth)
    {
        startMonth = newStartMonth;
    }

    public String endMonth()
    {
        return endMonth;
    }
    public void setEndMonth(String newEndMonth)
    {
        endMonth = newEndMonth;
    }

    public String startDay()
    {
        return startDay;
    }
    public void setStartDay(String newStartDay)
    {
        startDay = newStartDay;
    }

    public String endDay()
    {
        return endDay;
    }
    public void setEndDay(String newEndDay)
    {
        endDay = newEndDay;
    }

    public String startYear()
    {
        return startYear;
    }
    public void setStartYear(String newStartYear)
    {
        startYear = newStartYear;
    }

    public String endYear()
    {
        return endYear;
    }
    public void setEndYear(String newEndYear)
    {
        endYear = newEndYear;
    }

}
