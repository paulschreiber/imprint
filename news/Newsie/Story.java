// Story.java
// Created on Fri Sep 07 10:42:25 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Story extends EOGenericRecord {

    public Story() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Story(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
        super(context, classDesc, gid);
    }

    // If you add instance variables to store property values you
    // should add empty implementions of the Serialization methods
    // to avoid unnecessary overhead (the properties will be
    // serialized for you in the superclass).
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    }
*/

	//
	// return a href to this story, relative to the web server root,
	// referencing the Imprint.woa app (front end)
	//
	public String link() {
		Number storyId = (Number)storedValueForKey("id");
		return ( new String ( Helpers.BaseStoryUrl() + storyId ) );
	}

	//
	// return a href to this story, relative to the web server root,
	// referencing the Newsie.woa app (back end)
	//
	public String linkPreview() {
		Number storyId = (Number)storedValueForKey("id");
		return ( new String ( Helpers.BaseStoryPreviewUrl() + storyId ) );
	}
		
	//
	// return a href to the text only version of this story, relative
	// to the web server root, referencing the Imprint.woa app (front end)
	//
	public String linkLite() {
		Number storyId = (Number)storedValueForKey("id");
		return ( new String ( Helpers.BaseStoryLiteUrl() + storyId ) );
	}

	//
	// return a href to the NITF XML version of this story, relative
	// to the web server root, referencing the Imprint.woa app (front end)
	//
	public String linkNitf() {
		Number storyId = (Number)storedValueForKey("id");
		return ( new String ( Helpers.BaseStoryNitfUrl() + storyId ) );
	}
		

	//
	// return the lede (lead) paragraph of this story, if it exists
	// [NOT: otherwise, return the first body text paragraph, if it exists]
	// otherwise, return an empty string
	//
    public String lede() {
        String result = (String)storedValueForKey("contents");
		int parStartsAt = result.indexOf("<p class=\"lede\">") + 16;
		
		// we will get -1 for a false answer, so look for 15,
		// since we added 16
		if ( parStartsAt == 15 ) {
			//don't guess at the first paragraph
			//parStartsAt = result.indexOf("<p class=\"bodyText\">") + 20;
			return "";
		}
		
		// we will get -1 for a false answer, so look for 19,
		// since we added 20
		//if ( parStartsAt == 19 ) {
		//	return "";
		//}
		
		int parEndsAt = result.indexOf("</p>", parStartsAt);
		
		if ( parEndsAt > 0 ) {
			result = result.substring(parStartsAt, parEndsAt);
		}
		
		return result;
    }

	//
	// return true if the story has one author
	//
	public boolean hasOneAuthor() {
		if ( (author() != null) && (author2() == null) && (author3() == null) ) {
			return true;
		}
		
		return false;
	}
	
	//
	// return true if the story has two authors
	//
	public boolean hasTwoAuthors() {
		if ( (author() != null) && (author2() != null) && (author3() == null) ) {
			return true;
		}
		
		return false;
	}

	//
	// return true if the story has three authors
	//
	public boolean hasThreeAuthors() {
		if ( (author() != null) && (author2() != null) && (author3() != null) ) {
			return true;
		}
		
		return false;
	}

	//
	// return the number of authors
	//
	public int authorCount() {
		if ( hasThreeAuthors() ) {
			return 3;
		}

		if ( hasTwoAuthors() ) {
			return 2;
		}

		if ( hasOneAuthor() ) {
			return 1;
		}
		
		return 0;
	}

	//
	// return HTML containing the story's authors' names
	// with hyperlinks to their email addresses if
	// available
	//
	public String AuthorNames() {
		int numAuthors = authorCount();
		
		if ( numAuthors == 3 ) {
			String author3 = author3().nameAndEmail();
			String author2 = author2().nameAndEmail();
			String author1 = author().nameAndEmail();
			return author1 + ", " + author2 + " and " + author3;
		} else if ( numAuthors == 2 ) {
			String author2 = author2().nameAndEmail();
			String author1 = author().nameAndEmail();
			return author1 + " and " + author2;
		} else if ( numAuthors == 1 ) {
			String author1 = author().nameAndEmail();
			return author1;
		} else {
			return "";
		}
	}

	//
	// return text containing the story's authors' names
	//
	public String AuthorNamesText() {
		int numAuthors = authorCount();

		if ( numAuthors == 3 ) {
			String author3 = author3().preferredName();
			String author2 = author2().preferredName();
			String author1 = author().preferredName();
			return author1 + ", " + author2 + " and " + author3;
		} else if ( numAuthors == 2 ) {
			String author2 = author2().preferredName();
			String author1 = author().preferredName();
			return author1 + " and " + author2;
		} else if ( numAuthors == 1 ) {
			String author1 = author().preferredName();
			return author1;
		} else {
			return "";
		}
	}


	//
	// return text containing the story's authors' byline titles
	// if there is more than one author and all titles are the same,
	// return just one title. if there is more than one author and
	// not all titles are the same, return a string containing all of
	// the titles. finally, if there is only one title, retutn it.
	//
	//
	public String AuthorTitles() {
		int numAuthors = authorCount();

		if ( numAuthors == 3 ) {
			String byline3 = byline3().titleIfValidTitle();
			String byline2 = byline2().titleIfValidTitle();
			String byline1 = byline().titleIfValidTitle();
			
			if ( ( byline1.equals(byline2) ) && ( byline2.equals(byline3) ) ) {
				return byline1;
			} else {
				return byline1 + ", " + byline2 + " and " + byline3;
			}
			
		} else if ( numAuthors == 2 ) {
			String byline2 = byline2().titleIfValidTitle();
			String byline1 = byline().titleIfValidTitle();

			if ( byline1.equals(byline2) ) {
				return byline1;
			} else {
				return byline1 + " and " + byline2;
			}

		} else if ( numAuthors == 1 ) {
			return byline().titleIfValidTitle();
			
		} else {
			return "";
		}
	}

	//
	// return the headline and the column name, or, if they are the
	// same (i.e. the column has no real headline), return the column
	// name only
	//
    public String HeadlineWithColumn() {
        String headline = (String)storedValueForKey("headline");
		String columnName;
		
		if ( column() != null ) {
			columnName = column().columnName();
		} else {
			return headline;
		}
		
		if ( headline.toLowerCase().equals( columnName.toLowerCase() ) ) {
			return headline;
		} else {
			return columnName + ": " + headline;
		}
    }


    public String contents() {
        return (String)storedValueForKey("contents");
    }

    public void setContents(String value) {
        takeStoredValueForKey(value, "contents");
    }

    public String headline() {
        return (String)storedValueForKey("headline");
    }

    public void setHeadline(String value) {
        takeStoredValueForKey(value, "headline");
    }

    public String subhead() {
        return (String)storedValueForKey("subhead");
    }

    public void setSubhead(String value) {
        takeStoredValueForKey(value, "subhead");
    }

    public Number id() {
        return (Number)storedValueForKey("id");
    }

    public void setId(Number value) {
        takeStoredValueForKey(value, "id");
    }

    public Number columnId() {
        return (Number)storedValueForKey("columnId");
    }

    public void setColumnId(Number value) {
        takeStoredValueForKey(value, "columnId");
    }

    public Author author() {
        return (Author)storedValueForKey("author");
    }

    public void setAuthor(Author value) {
        takeStoredValueForKey(value, "author");
    }

    public Column column() {
        return (Column)storedValueForKey("column");
    }

    public void setColumn(Column value) {
        takeStoredValueForKey(value, "column");
    }

    public StoryType storyType() {
        return (StoryType)storedValueForKey("storyType");
    }

    public void setStoryType(StoryType value) {
        takeStoredValueForKey(value, "storyType");
    }

    public Issue issue() {
        return (Issue)storedValueForKey("issue");
    }

    public void setIssue(Issue value) {
        takeStoredValueForKey(value, "issue");
    }

    public Section section() {
        return (Section)storedValueForKey("section");
    }

    public void setSection(Section value) {
        takeStoredValueForKey(value, "section");
    }

    public Author author2() {
        return (Author)storedValueForKey("author2");
    }

    public void setAuthor2(Author value) {
        takeStoredValueForKey(value, "author2");
    }

    public Author author3() {
        return (Author)storedValueForKey("author3");
    }

    public void setAuthor3(Author value) {
        takeStoredValueForKey(value, "author3");
    }

    public AuthorTitle byline() {
        return (AuthorTitle)storedValueForKey("byline");
    }

    public void setByline(AuthorTitle value) {
        takeStoredValueForKey(value, "byline");
    }

    public AuthorTitle byline2() {
        return (AuthorTitle)storedValueForKey("byline2");
    }

    public void setByline2(AuthorTitle value) {
        takeStoredValueForKey(value, "byline2");
    }

    public AuthorTitle byline3() {
        return (AuthorTitle)storedValueForKey("byline3");
    }

    public void setByline3(AuthorTitle value) {
        takeStoredValueForKey(value, "byline3");
    }

    public NSArray storyAssets() {
        return (NSArray)storedValueForKey("storyAssets");
    }

    public void setStoryAssets(NSMutableArray value) {
        takeStoredValueForKey(value, "storyAssets");
    }

    public void addToStoryAssets(StoryAsset object) {
        NSMutableArray array = (NSMutableArray)storyAssets();

        willChange();
        array.addObject(object);
    }

    public void removeFromStoryAssets(StoryAsset object) {
        NSMutableArray array = (NSMutableArray)storyAssets();

        willChange();
        array.removeObject(object);
    }
}
