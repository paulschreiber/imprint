// Asset.java
// Created on Thu Oct 11 18:36:11 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Asset extends EOGenericRecord {

    public Asset() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Asset(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return the first CaptionPrefixSize() characters of the caption,
	// or return the whole caption if it has fewer than
	// CaptionPrefixSize() characters
	//
	// this is typically used in the popup buttons
	//
    public String captionPrefix() {
        String result = (String)storedValueForKey("caption");
		int prefixLength = Helpers.CaptionPrefixSize();
		
		if ( result == null ) {
			System.out.println( "null asset found!!" + id() );
			return "";
		}
		
		// avoid string out-of-bounds errors
		if ( result.length() < prefixLength ) {
			prefixLength = result.length();
		}
		
		result = result.substring(0, prefixLength);
		return result;

    }

	//
	// return true if the asset has one author
	//
	public boolean hasOneAuthor() {
		if ( (author() != null) && (author2() == null) ) {
			return true;
		}
		
		return false;
	}
	
	//
	// return the number of authors
	//
	public int authorCount() {
		if ( hasTwoAuthors() ) {
			return 2;
		}

		if ( hasOneAuthor() ) {
			return 1;
		}
		
		return 0;
	}

	//
	// return true if the asset has one author
	//
	public boolean hasTwoAuthors() {
		if ( (author() != null) && (author2() != null) ) {
			return true;
		}
		
		return false;
	}



	//
	// return HTML containing the asset's authors' names
	// with hyperlinks to their email addresses if
	// available; for the hyperlinks, escape the slashes
	//
	public String AuthorNamesJavaScript()
	{
		String result = AuthorNames();
		result = Helpers.MyStringReplace(result,"</a>", "<\\/a>");
		
		return result;
	}
	
	//
	// return HTML containing the asset's authors' names
	// with hyperlinks to their email addresses if
	// available
	//
	public String AuthorNames() {	
		int numAuthors = authorCount();
		
		if ( numAuthors == 2 ) {
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
	// return text containing the asset's authors' names
	//
	public String AuthorNamesText() {
		int numAuthors = authorCount();
		
		if ( numAuthors == 2 ) {
			String author1 = author().preferredName();
			String author2 = author2().preferredName();
			return author1 + " and " + author2;
		} else if ( numAuthors == 1 ) {
			String author1 = author().preferredName();
			return author1;
		} else {
			return "";
		}
	}

	//
	// get the asset's file extension
	//
    public String fileExtension() {
        String filePath = (String)storedValueForKey("location");
		String extension = NSPathUtilities.pathExtension( filePath );
		
		return extension;
    }

	//
	// get the asset's file name (including extension)
	//
    public String fileName() {
        String filePath = (String)storedValueForKey("location");
		String name = Helpers.lastPathComponent( filePath );
		
		return name;
    }
	
	//
	// return true if the asset is an image file (png, gif, jpeg, etc.)
	//
	public boolean isImage() {
		String extension = fileExtension().toLowerCase();		
		return ( Helpers.imageTypeList.containsObject( extension ) );
	}

	//
	// return true if the asset is a quicktime or shockwave movie file
	//
	public boolean isMovie() {
		String extension = fileExtension().toLowerCase();		
		return ( Helpers.movieTypeList.containsObject( extension ) );
	}

	//
	// return true if the asset is a sound file (aiff, wav, etc)
	//
	public boolean isSound() {
		String extension = fileExtension().toLowerCase();		
		return ( Helpers.soundTypeList.containsObject( extension ) );
	}

	//
	// return true if the asset is not a known image or embedded type
	//
	// (not: return true if the asset is a downloadable file (pdf, xls, etc.))
	//
	public boolean isDownloadable() {
		//String extension = fileExtension().toLower();		
		//return ( Helpers.downloadTypeList.containsObject( extension ) );
		
		return ( ! isEmbedded() && ! isImage() );
	}
	
	//
	// return true if we use the quicktime plugin to view this
	// i.e. movies, sounds
	//
	public boolean isEmbedded() {
		return ( isSound() || isMovie() );
	}
	
    public String location() {
        return (String)storedValueForKey("location");
    }

    public void setLocation(String value) {
        takeStoredValueForKey(value, "location");
    }

    public String caption() {
        return (String)storedValueForKey("caption");
    }

    public void setCaption(String value) {
        takeStoredValueForKey(value, "caption");
    }

    public Number issueId() {
        return (Number)storedValueForKey("issueId");
    }

    public void setIssueId(Number value) {
        takeStoredValueForKey(value, "issueId");
    }

    public Number height() {
        return (Number)storedValueForKey("height");
    }

    public void setHeight(Number value) {
        takeStoredValueForKey(value, "height");
    }

    public Number width() {
        return (Number)storedValueForKey("width");
    }

    public void setWidth(Number value) {
        takeStoredValueForKey(value, "width");
    }

    public Number hideCaption() {
        return (Number)storedValueForKey("hideCaption");
    }

    public void setHideCaption(Number value) {
        takeStoredValueForKey(value, "hideCaption");
    }

    public Number id() {
        return (Number)storedValueForKey("id");
    }

    public void setId(Number value) {
        takeStoredValueForKey(value, "id");
    }

    public Author author() {
        return (Author)storedValueForKey("author");
    }

    public void setAuthor(Author value) {
        takeStoredValueForKey(value, "author");
    }

    public Issue issue() {
        return (Issue)storedValueForKey("issue");
    }

    public void setIssue(Issue value) {
        takeStoredValueForKey(value, "issue");
    }

    public Author author2() {
        return (Author)storedValueForKey("author2");
    }

    public void setAuthor2(Author value) {
        takeStoredValueForKey(value, "author2");
    }

    public NSArray storyasset() {
        return (NSArray)storedValueForKey("storyasset");
    }

    public void setStoryasset(NSMutableArray value) {
        takeStoredValueForKey(value, "storyasset");
    }

    public void addToStoryasset(StoryAsset object) {
        NSMutableArray array = (NSMutableArray)storyasset();

        willChange();
        array.addObject(object);
    }

    public void removeFromStoryasset(StoryAsset object) {
        NSMutableArray array = (NSMutableArray)storyasset();

        willChange();
        array.removeObject(object);
    }
}
