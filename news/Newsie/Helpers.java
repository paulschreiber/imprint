//
// Helpers.java
// Project Newsie
//
// Created by paul on Sun Aug 26 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.activation.*;

public class Helpers extends Object {

	//
	// sort orderings to be used in fetch specifications and displayGroups
	//
	protected static EOSortOrdering issueSortOrdering;
	public static NSMutableArray issueOrdering;

	protected static EOSortOrdering issueFrontSortOrdering;
	public static NSMutableArray issueFrontOrdering;

	protected static EOSortOrdering mastheadSortOrdering;
	public static NSMutableArray mastheadOrdering;

	protected static EOSortOrdering columnSortOrdering;
	public static NSMutableArray columnOrdering;

	protected static EOSortOrdering storyTypeSortOrdering;
	public static NSMutableArray storyTypeOrdering;

	protected static EOSortOrdering sectionSortOrdering;
	public static NSMutableArray sectionOrdering;

	protected static EOSortOrdering sectionFrontDateSortOrdering;
	protected static EOSortOrdering sectionFrontSectionSortOrdering;
	public static NSMutableArray sectionFrontOrdering;

	protected static EOSortOrdering authorTitleSortOrdering;
	public static NSMutableArray authorTitleOrdering;

	protected static EOSortOrdering assetDateSortOrdering;
	protected static EOSortOrdering assetCaptionSortOrdering;
	public static NSMutableArray assetOrdering;

	protected static EOSortOrdering storyAssetDateSortOrdering;
	protected static EOSortOrdering storyAssetStorySortOrdering;
	protected static EOSortOrdering storyAssetCaptionSortOrdering;
	public static NSMutableArray storyAssetOrdering;

	protected static EOSortOrdering lastNameSortOrdering;
	protected static EOSortOrdering firstNameSortOrdering;
	public static NSMutableArray authorOrdering;
	
	protected static EOSortOrdering storyDateSortOrdering;
	protected static EOSortOrdering storySectionSortOrdering;
	public static NSMutableArray storyOrdering;

	protected static EOSortOrdering adPlacementCreativeSortOrdering;
	protected static EOSortOrdering adPlacementFrequencySortOrdering;
	public static NSMutableArray adPlacementOrdering;
	public static NSMutableArray adPlacementFreqOrdering;
    
	protected static EOSortOrdering adSectionSortOrdering;
	public static NSMutableArray adSectionOrdering;

	protected static EOSortOrdering classifiedAdsSortOrdering;
	public static NSMutableArray classifiedAdsOrdering;
    
	protected static EOSortOrdering adCreativeDateSortOrdering;
	protected static EOSortOrdering adCreativeNameSortOrdering;
	public static NSMutableArray adCreativeOrdering;
    
	protected static EOSortOrdering adClassifiedIssueSortOrdering;
	protected static EOSortOrdering adClassifiedSectionSortOrdering;
	protected static EOSortOrdering adClassifiedAdSortOrdering;
	public static NSMutableArray adClassifiedOrdering;

	protected static EOSortOrdering advertiserLastNameSortOrdering;
	protected static EOSortOrdering advertiserFirstNameSortOrdering;
	public static NSMutableArray advertiserOrdering;

	protected static EOSortOrdering assetsInStorySortOrdering;
	public static NSMutableArray assetsInStoryOrdering;

	//
	// define the sort orderings to be used in fetch specifications
	// and displayGroups. make sure to call this in in the Application
	// constructor, otherwise you won't get sorted data
	//
	public static void setSortOrderings() {
		// issue sorting
		issueSortOrdering = EOSortOrdering.sortOrderingWithKey("date", EOSortOrdering.CompareDescending);
		issueOrdering = new NSMutableArray();
		issueOrdering.addObject(issueSortOrdering);

		// issue front sorting
		issueFrontSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		issueFrontOrdering = new NSMutableArray();
		issueFrontOrdering.addObject(issueFrontSortOrdering);

		// masthead sorting
		mastheadSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		mastheadOrdering = new NSMutableArray();
		mastheadOrdering.addObject(mastheadSortOrdering);

		// column sorting
		columnSortOrdering = EOSortOrdering.sortOrderingWithKey("columnName", EOSortOrdering.CompareAscending);
		columnOrdering = new NSMutableArray();
		columnOrdering.addObject(columnSortOrdering);

		// story type sorting
		storyTypeSortOrdering = EOSortOrdering.sortOrderingWithKey("storyType", EOSortOrdering.CompareAscending);
		storyTypeOrdering = new NSMutableArray();
		storyTypeOrdering.addObject(storyTypeSortOrdering);

		// author sorting -- last name, first name
		lastNameSortOrdering = EOSortOrdering.sortOrderingWithKey("lastName", EOSortOrdering.CompareAscending);
		firstNameSortOrdering = EOSortOrdering.sortOrderingWithKey("lastName", EOSortOrdering.CompareAscending);
		authorOrdering = new NSMutableArray();
		authorOrdering.addObject(lastNameSortOrdering);
		authorOrdering.addObject(firstNameSortOrdering);

		// author title (byline) sorting
		authorTitleSortOrdering = EOSortOrdering.sortOrderingWithKey("title", EOSortOrdering.CompareAscending);
		authorTitleOrdering = new NSMutableArray();
		authorTitleOrdering.addObject(authorTitleSortOrdering);

		// asset sorting -- issue date, caption
		assetDateSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		assetCaptionSortOrdering = EOSortOrdering.sortOrderingWithKey("caption", EOSortOrdering.CompareAscending);
		assetOrdering = new NSMutableArray();
		assetOrdering.addObject(assetDateSortOrdering);
		assetOrdering.addObject(assetCaptionSortOrdering);

		// story asset sorting -- date, story, asset caption
		storyAssetDateSortOrdering = EOSortOrdering.sortOrderingWithKey("story.issue.date", EOSortOrdering.CompareAscending);
		storyAssetStorySortOrdering = EOSortOrdering.sortOrderingWithKey("story.headline", EOSortOrdering.CompareAscending);
		storyAssetCaptionSortOrdering = EOSortOrdering.sortOrderingWithKey("asset.caption", EOSortOrdering.CompareAscending);
		storyAssetOrdering = new NSMutableArray();
		storyAssetOrdering.addObject(storyAssetDateSortOrdering);
		storyAssetOrdering.addObject(storyAssetStorySortOrdering);
		storyAssetOrdering.addObject(storyAssetCaptionSortOrdering);

		// section sorting
		sectionSortOrdering = EOSortOrdering.sortOrderingWithKey("sectionName", EOSortOrdering.CompareAscending);
		sectionOrdering = new NSMutableArray();
		sectionOrdering.addObject(sectionSortOrdering);

		// section front sorting -- issue date, section name
		sectionFrontDateSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		sectionFrontSectionSortOrdering = EOSortOrdering.sortOrderingWithKey("section.sectionName", EOSortOrdering.CompareAscending);
		sectionFrontOrdering = new NSMutableArray();
		sectionFrontOrdering.addObject(sectionFrontDateSortOrdering);
		sectionFrontOrdering.addObject(sectionFrontSectionSortOrdering);

		// story sorting -- date, section, headline
		storyDateSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		storySectionSortOrdering = EOSortOrdering.sortOrderingWithKey("section.sectionName", EOSortOrdering.CompareAscending);
		storyOrdering = new NSMutableArray();
		storyOrdering.addObject(storyDateSortOrdering);
		storyOrdering.addObject(storySectionSortOrdering);

		// photos in story sorting
		assetsInStorySortOrdering = EOSortOrdering.sortOrderingWithKey("orderInStory", EOSortOrdering.CompareAscending);
		assetsInStoryOrdering = new NSMutableArray();
		assetsInStoryOrdering.addObject(assetsInStorySortOrdering);

		// ad placement sorting -- issue date, name
		adPlacementCreativeSortOrdering = EOSortOrdering.sortOrderingWithKey("adCreative.name", EOSortOrdering.CompareAscending);
		adPlacementFrequencySortOrdering = EOSortOrdering.sortOrderingWithKey("frequency", EOSortOrdering.CompareDescending);
		adPlacementOrdering = new NSMutableArray();
		adPlacementOrdering.addObject(adPlacementCreativeSortOrdering);
		adPlacementOrdering.addObject(adPlacementFrequencySortOrdering);

		adPlacementFreqOrdering = new NSMutableArray();
		adPlacementFreqOrdering.addObject(adPlacementFrequencySortOrdering);
		adPlacementFreqOrdering.addObject(adPlacementCreativeSortOrdering);

		// ad section sorting
		adSectionSortOrdering = EOSortOrdering.sortOrderingWithKey("sectionName", EOSortOrdering.CompareAscending);
		adSectionOrdering = new NSMutableArray();
		adSectionOrdering.addObject(adSectionSortOrdering);

		// classified ads in section sorting
		classifiedAdsSortOrdering = EOSortOrdering.sortOrderingWithKey("ad", EOSortOrdering.CompareAscending);
		classifiedAdsOrdering = new NSMutableArray();
		classifiedAdsOrdering.addObject(classifiedAdsSortOrdering);

		// advertiser sorting
		advertiserLastNameSortOrdering = EOSortOrdering.sortOrderingWithKey("lastName", EOSortOrdering.CompareAscending);
		advertiserFirstNameSortOrdering = EOSortOrdering.sortOrderingWithKey("firstName", EOSortOrdering.CompareAscending);
		advertiserOrdering = new NSMutableArray();
		advertiserOrdering.addObject(advertiserLastNameSortOrdering);
		advertiserOrdering.addObject(advertiserFirstNameSortOrdering);

		// ad creative sorting -- issue date, name
		adCreativeDateSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		adCreativeNameSortOrdering = EOSortOrdering.sortOrderingWithKey("name", EOSortOrdering.CompareAscending);
		adCreativeOrdering = new NSMutableArray();
		adCreativeOrdering.addObject(adCreativeDateSortOrdering);
		adCreativeOrdering.addObject(adCreativeNameSortOrdering);

		// ad classified sorting -- issue date, section, ad
		adClassifiedIssueSortOrdering = EOSortOrdering.sortOrderingWithKey("issue.date", EOSortOrdering.CompareDescending);
		adClassifiedSectionSortOrdering = EOSortOrdering.sortOrderingWithKey("adSection.sectionName", EOSortOrdering.CompareAscending);
		adClassifiedAdSortOrdering = EOSortOrdering.sortOrderingWithKey("ad", EOSortOrdering.CompareAscending);
		adClassifiedOrdering = new NSMutableArray();
		adClassifiedOrdering.addObject(adClassifiedIssueSortOrdering);
		adClassifiedOrdering.addObject(adClassifiedSectionSortOrdering);
		adClassifiedOrdering.addObject(adClassifiedAdSortOrdering);

	}

	//
	// formatters to use in parsing dates and numbers
	//
	public static NSTimestampFormatter IsoDateFormatter = new NSTimestampFormatter("%Y-%m-%d");
	public static NSTimestampFormatter CompactDateFormatter = new NSTimestampFormatter("%Y%m%d");
	public static NSTimestampFormatter FullDateFormatter = new NSTimestampFormatter("%B %d, %Y");
    public static NSNumberFormatter IntegerNumberFormatter = new NSNumberFormatter("#0");

	//
	// popup menu for use in the "Contact Us" module
	//
    public static final NSArray feedbackList = new NSArray(
		new Object[] {
			"Volunteer at Imprint",
			"Send a letter to the editor",
			"Submit a correction request",
			"Submit an article"
			}
	);

	//
	// popup menu for use in the "Advertise" module
	//
    public static final NSArray adInfoList = new NSArray(
		new Object[] {
			"Submit a classified ad",
			"Have an advertising representative contact me"
			}
	);

	//
	// popup menu for use in the "Story Manager" module
	// for the story Assistant
	//
    public static final NSArray storyHelperList = new NSArray(
		new Object[] {
			"Campus Question",
			"Letter to the Editor",
			"CKMS Top Ten",
			"Editorial",
			"Review",
			"Cartoon",
			"Photo",
			"Slide Show",
			"Table"
		}
	);



	//
	// popup menu for use in the SectionFront and IssueFront modules
	//
    public static final NSArray frontNameList = new NSArray(
		new Object[] { "Images on Right", "Images on Left" }
	);

	//
	// a hash (dictionary) from the names in the popup menu
	// to the name of the WOComponent
	//
    public static final NSDictionary frontDict = new NSDictionary(
		new Object[] { "Front1", "Front2" },
		new Object[] { "Images on Right", "Images on Left" }
	);

	//
	// a hash (dictionary) from the names in the popup menu
	// to the names of the mailing lists
	//
    public static final NSDictionary mailingListsDict = new NSDictionary(
		new Object[] { "arts", "features", "news", "photo", "science", "sports", "web", "sysadmin" },
		new Object[] { "arts-writers", "features-writer", "news-writers", "photographers", "science-writers", "sports-writers", "tech", "tech" }
	);

	//
	// popup menu for use in the search module
	//
    public static final NSArray monthList = new NSArray(
		new Object[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" } );

	//
	// month list to construct the date from -- we get the index in the list of
	// text months and grab its numeric equivalent
	//
    public static final NSArray numericMonthList = new NSArray(
		new Object[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}
	);
	
	//
	// popup menu for use in the search module
	//
	public static final NSArray dayList = new NSArray( new Object[]
		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		 "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		 "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
	}
	);

	//
	// day list to construct the date from -- we get the index in the list of
	// days [with no leading zero] and grab its two-digit equivalent
	//
	public static final NSArray paddedDayList = new NSArray( new Object[]
		{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
		 "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		 "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
	}
	);

	//
	// popup menu for use in the search module -- lists years from
	// publication start until now
	//
    public static NSMutableArray yearList() {
		NSMutableArray yearArray = new NSMutableArray();
		NSTimestampFormatter formatter = new NSTimestampFormatter("%Y");
		int startYear = PublicationStartYear();
		int currentYear = Integer.parseInt( formatter.format(new NSTimestamp()) );
		
		for (int year = currentYear; year >= startYear; year--) {
			yearArray.addObject( new Integer(year) );
		}

		return yearArray;
	}

	//
	// for asset management, types of images
	//
    public static final NSArray imageTypeList = new NSArray(
		new Object[] { "jpeg", "jpg", "png", "gif", "tif", "tiff" }
	);

	//
	// for asset management, types of movies
	//
    public static final NSArray movieTypeList = new NSArray(
		new Object[] { "mov", "swf", "avi", "mpeg" }
	);

	//
	// for asset management, types of sound files
	//
    public static final NSArray soundTypeList = new NSArray(
		new Object[] { "aiff", "au", "wav", "aif", "mp3" }
	);

	//
	// for asset management, types of downloadable files
	//
    public static final NSArray downloadTypeList = new NSArray(
		new Object[] { "doc", "wpd", "xls", "ppt", "pdf" }
	);

	//
	// here are the constants which are read in from
	// the Properties file
	//

	// this is used for italicizing the name in stories
	public static String PublicationName() {
		return System.getProperty("PublicationName");
	}

	// canadian flag for CKMS Top Ten
	public static String CanConIndicator() {
		return System.getProperty("CanConIndicator");
	}

	public static String BlankByline() {
		return System.getProperty("BlankByline");
	}

	public static String LetterToEditorIntro() {
		return System.getProperty("LetterToEditorIntro");
	}

	public static String EditorsResponse() {
		return System.getProperty("EditorsResponse");
	}

	public static String MailingListHost() {
		return System.getProperty("MailingListHost");
	}

	public static String FeedbackSender() {
		return System.getProperty("FeedbackSender");
	}

	public static String FeedbackRecipient() {
		return System.getProperty("FeedbackRecipient");
	}

	public static String LettersRecipient() {
		return System.getProperty("LettersRecipient");
	}

	public static String ClassifiedsRecipient() {
		return System.getProperty("ClassifiedsRecipient");
	}

	public static String ClassifiedsSender() {
		return System.getProperty("ClassifiedsSender");
	}

	public static String AdContactRecipient() {
		return System.getProperty("AdContactRecipient");
	}

	public static String AdContactSender() {
		return System.getProperty("AdContactSender");
	}

	public static String SmtpHost() {
		return System.getProperty("SmtpHost");
	}
	
	public static int BatchSize() {
		if ( MySession.batchSize != -1 ) {
			return MySession.batchSize;
		} else {
			return Integer.parseInt(System.getProperty("BatchSize"));
		}
	}
	
	public static int DefaultSize() {
		return Integer.parseInt(System.getProperty("BatchSize"));
	}
	
	public static int AdPrefixSize() {
		return Integer.parseInt(System.getProperty("AdPrefixSize"));
	}

	public static int CaptionPrefixSize() {
		return Integer.parseInt(System.getProperty("CaptionPrefixSize"));
	}

	public static int PublicationStartYear() {
		return Integer.parseInt(System.getProperty("PublicationStartYear"));
	}
	
	public static boolean JavaMailDebug() {
		return (System.getProperty("JavaMailDebug").equals("true"));
	}

	public static String TitleSpacer() {
		return System.getProperty("TitleSpacer");
	}

	public static String BackendTitleSpacer() {
		return System.getProperty("BackendTitleSpacer");
	}

	public static String AssetPath() {
		return System.getProperty("AssetPath");
	}
    
	public static String AssetUrl() {
		return System.getProperty("AssetUrl");
	}
    
	public static String AdUrl() {
		return System.getProperty("AdUrl");
	}
	
	//
	// constants for URLs that are used in link() linkPreview()
	// and so on, by IssueFront, SectionFront and Story
	// 
	public static String BaseUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/";
	}

	public static String BaseStoryPreviewUrl() {
		return "/cgi-bin/WebObjects/Newsie.woa/wa/story?storyId=";
	}
    
	public static String BaseStoryUrl() {
		return "/story/";
	}

	public static String BaseStoryLiteUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/lite?storyId=";
	}

	public static String BaseStoryNitfUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/nitf?storyId=";
	}

	public static String BaseSectionUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/section?section=";
	}

	public static String BaseSectionLiteUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/liteSection?section=";
	}

	public static String BaseSectionPreviewUrl() {
		return "/cgi-bin/WebObjects/Newsie.woa/wa/section?section=";
	}

	public static String BaseClassifiedsUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/classifiedAds?section=";
	}

	public static String BaseClassifiedsPreviewUrl() {
		return "/cgi-bin/WebObjects/Adverts.woa/wa/classifiedAds?section=";
	}

	public static String BaseIssueUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/issue?date=";
	}

	public static String BaseIssueLiteUrl() {
		return "/cgi-bin/WebObjects/Imprint.woa/wa/liteIssue?date=";
	}

	public static String BaseIssuePreviewUrl() {
		return "/cgi-bin/WebObjects/Newsie.woa/wa/issue?date=";
	}

	public static String BaseMastheadPreviewUrl() {
		return "/cgi-bin/WebObjects/Newsie.woa/wa/masthead?date=";
	}

	//
	// replacement for NSPathUtilities.lastPathComponent so
	// we can handle windows as well as Mac/Unix uploads
	//
	public static String lastPathComponent( String aString ) {
		if ( aString == null || aString.length() == 0 ) {
			return "";
		}
		
		int i = aString.lastIndexOf( "\\" );

        if ( i == -1 ) {
			i = aString.lastIndexOf( "/" );
		}

		return aString.substring( i+1, aString.length() );
    }
	
	//
	// given: a file name, file data, a directory in which to store the
	// file (relative to the hard drive root) and a subdirectory
	//  (/images, /ads, ...), store the file there and return the relative path
	//
	public static String handleUpload (String newFileName, NSData newFileContents, String directoryName, String fileUrl) {
		// Determine the path to save the upload file at:  if specified path exists, place it
		// there, otherwise place it in the same directory as the running application
		File tmpPath = new File( AssetPath() + fileUrl );
		String filePath = "";
		
		if ( tmpPath.exists() ) {
			filePath = tmpPath.getAbsolutePath();
		} else {
			return "FAILED";
		}
		
		// Get the name for the uploaded file from the instance variable:  the full
		// information is the path location on the client information.
		String fileName = lastPathComponent(newFileName);
		
		// Create the output path for the file on the application server
		String outputFilePath = new String( filePath + File.separator + directoryName + File.separator + fileName );
		
		// make sure the directory exists; if not, create it
		File assetDir = new File ( AssetPath() + fileUrl + File.separator + directoryName );
		if ( ! assetDir.isDirectory() ) {
			assetDir.mkdirs();
		}
		
		// Write the file out to the location
		try {
			FileOutputStream fileOutputStream = new FileOutputStream( outputFilePath );
			newFileContents.writeToStream(fileOutputStream);
			fileOutputStream.close();
		} catch (IOException exception) {
			return "FAILED";
		}

		// create this string: /images + / + 20010831 + / + foo.gif
		String aFileLocation = fileUrl + File.separator + directoryName + File.separator + fileName;

		return aFileLocation;
		
	}

	//
	// return true if the file contains data (i.e. the upload worked)
	//
    public static boolean hasUploadData(NSData newFileContents) {
        if ( newFileContents != null && newFileContents.length() > 0 ) {
            return true;
        }
        return false;
    }

	
    /**
     * Method to return a (possibly null) form value from the request for
     * a specific key.  This method looks in the WORequest for a particular key
     * and only returns a value if the form value has a length greater than zero.
     * Otherwise it returns null.
     *
     * @param aRequest		the WORequest with the form values
     * @param aKey		the form value to check for
     * @return			the form value, or null
     */
       
    public static String nullEnabledFormValueForKey( WORequest aRequest, String aKey ) {
    
        String temp = (String)aRequest.formValueForKey( aKey );
        if ( (temp != null) && (temp.length() == 0) ) {
            return null;
        }
        return temp;
    }



    /**
     * Method to return a Number for a form value from the request for a specific key.
     * Since all of the form values come back in String format, we have to 
     * coerce the value (if not null) into the proper format.
     * 
     * @param aRequest		the WORequest with the form values
     * @param aKey		the form value to check for
     * @return			the Number representation for the value
     */

    public static Number numericFormValueFromRequest( WORequest aRequest, String aKey) {
    
        String numberString = nullEnabledFormValueForKey( aRequest, aKey );
        Number number = null;
        if ( numberString != null ) {
            try {
                number = (Number)IntegerNumberFormatter.parseObject(numberString);
            } catch ( java.text.ParseException e ) {
            	if (NSLog.debugLoggingAllowedForLevelAndGroups( NSLog.DebugLevelInformational, NSLog.DebugGroupValidation) )
                NSLog.out.appendln( e );
            }
        }
        return number;
    }


	//
	// get the stories for the issue selected in the popup button
	// and refresh the list at the bottom (i.e. when the user has
	// clicked "Get Stories")
	//
	// used in StoryManager, AssetManager, StoryAssetManager
	//
	public static void GetIssueStories(Issue selectedIssueItem,
		Section selectedSectionItem, WODisplayGroup displayGroup,
		String issuePrefix, String sectionPrefix
		) {
		
		if ( issuePrefix == null )
			issuePrefix = new String();
	
		if ( sectionPrefix == null )
			sectionPrefix = new String();
	
		NSMutableArray args = new NSMutableArray();
		EOQualifier selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat("", args);
	
		if ( (selectedIssueItem != null) || (selectedSectionItem != null) ) {
		
			if ( (selectedIssueItem != null) && (selectedSectionItem != null) ) {
				args.addObject( selectedIssueItem.date() );
				args.addObject( selectedSectionItem );
				selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat(issuePrefix + "issue.date = %@ and "+sectionPrefix+"section = %@", args);
			} else if ( selectedIssueItem != null ) {
				args.addObject( selectedIssueItem.date() );
				selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat(issuePrefix + "issue.date = %@", args);
			} else if ( selectedSectionItem != null ) {
				args.addObject( selectedSectionItem );
				selectedIssueQualifier = EOQualifier.qualifierWithQualifierFormat(sectionPrefix + "section = %@", args);
			}
				
			displayGroup.setQualifier( selectedIssueQualifier );		
			displayGroup.setCurrentBatchIndex( 1 );
			displayGroup.fetch();

		} else {
			displayGroup.setQualifier( null );		
			displayGroup.setCurrentBatchIndex( 1 );
			displayGroup.fetch();
		}

	}

	//
	// get a list of stories and letters for the current issue and section
	// to populate the popup buttons 
	//
	public static NSMutableArray GetSectionStories( Section sectionItem, NSTimestamp issueDate,  EOEditingContext editingContext )
	{
		EOQualifier currentSectionQualifier;
		EOFetchSpecification storyFetchSpec;

		NSMutableArray args = new NSMutableArray();
		NSMutableArray tmpStoryList = new NSMutableArray();
		NSMutableArray storyList = new NSMutableArray();
		NSMutableArray letterList = new NSMutableArray();
		NSMutableArray result = new NSMutableArray();
		Story currentStory;
		
		args.addObject( issueDate );
		args.addObject( sectionItem.sectionName() );
		currentSectionQualifier = EOQualifier.qualifierWithQualifierFormat("issue.date = %@ and section.sectionName = %@", args);

		storyFetchSpec = new EOFetchSpecification("Story", currentSectionQualifier, storyOrdering);
		tmpStoryList = new NSMutableArray(editingContext.objectsWithFetchSpecification(storyFetchSpec));
		
		//
		// put the letters to the editor in one list,
		// all the other stories in another list
		//
		for (int i=0; i < tmpStoryList.count(); i++) {
			currentStory = (Story) tmpStoryList.objectAtIndex(i);
			
			if ( (currentStory.storyType() != null) &&
						(currentStory.storyType().storyType().equals("Letter to the editor")) ) {
				letterList.addObject( currentStory );
			} else {
				storyList.addObject( currentStory );
			}
		} // for

		result.addObject(storyList);
		result.addObject(letterList);
		
		return result;
	}
	
	//
	// given a sender, recipient, subject, message text, attachment
	// location (relative to hard drive root) and attachment name, send the email
	//
	public static void sendEmailWithAttachment(String fromEmail, String toEmail, String subject, String messageBody, String attachmentLocation, String attachmentName) {

		// set the mail host
	    Properties props = System.getProperties();
		props.put("mail.smtp.host", SmtpHost());

		javax.mail.Session session = Session.getDefaultInstance(props);
        session.setDebug( JavaMailDebug() );
		
		try {
			MimeMessage msg = new MimeMessage(session); 
			msg.setFrom( new InternetAddress(fromEmail) );
			
			InternetAddress[] toAddress = { new InternetAddress(toEmail) };
			msg.setRecipients( Message.RecipientType.TO, toAddress );
			msg.setSubject( subject ); 
			msg.setSentDate(new java.util.Date()); 
	
			// create and fill the first message part 
			MimeBodyPart mbp1 = new MimeBodyPart(); 
			mbp1.setText(messageBody); 
			
			// create the second message part 
			MimeBodyPart mbp2 = new MimeBodyPart(); 
			
			// attach the file to the message 	
			FileDataSource fds= new FileDataSource(attachmentLocation); 
			mbp2.setDataHandler(new DataHandler(fds)); 
			mbp2.setFileName(attachmentName); 
			
			// create the Multipart and its parts to it 
			Multipart mp = new MimeMultipart(); 
			mp.addBodyPart(mbp1); 
			mp.addBodyPart(mbp2);
			
			// add the Multipart to the message 
			msg.setContent(mp); 
			Transport.send(msg); 	
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		}

	}

	//
	// given a sender, recipient, subject and message body send the email
	//
    public static void sendEmail(String fromEmail, String toEmail, String subject, String messageBody) {
	
		Object tos[]={ toEmail };
		NSArray to = new NSArray(tos); 

		String mailInfo = WOMailDelivery.sharedInstance().composePlainTextEmail( fromEmail, to, null, subject, messageBody, true); 
			
    }

	//
	// given a listname and an email address, have that email address
	// send a subscribe message to the list
	//
	public static void mailingListSubscribe(String listName, String fromEmail ) {
		String recipient =  listName + "-request@" + MailingListHost();
		NSArray to = new NSArray(recipient);
		String messageBody = "subscribe";

		String mailInfo = WOMailDelivery.sharedInstance().composePlainTextEmail( fromEmail, to, null, "", messageBody, true); 
	}


	//
	// standard string replacement function
	// 
	public static String MyStringReplace (String source, String toFind, String toInsert)
	{
		StringBuffer buf= new StringBuffer();
		int cursor=0;
		while ( true ) {
			int foundAt=source.indexOf(toFind, cursor); 
			if ( foundAt >= 0) {
				buf.append( source.substring(cursor, foundAt) );
				buf.append(toInsert);
			} else {
				buf.append(source.substring(cursor));
				return buf.toString();
			}
			cursor = foundAt + toFind.length();
		} // while
	}

	//
	// remove IMG, EMBED, OBJECT, APPLET tags
	//
	
	public static String StripGraphicTags ( String text ) {
		if ( (text == null) || (text.length() == 0) ) {
			return "";
		}

		String result;
		
		result = StripTag( "img", text );
		result = StripTag( "embed", result );
		result = StripTag( "object", result );
		result = StripTag( "applet", result );
		
		return result;
	}

	//
	// remove TABLE, TR, TD, TH
	//
	
	public static String StripTableTags ( String text ) {
		if ( (text == null) || (text.length() == 0) ) {
			return "";
		}

		String result;
		
		result = StripTag( "table", text );
		result = StripTag( "tr", result );
		result = StripTag( "td", result );
		result = StripTag( "th", result );
		
		return result;
	}

	//
	// remove a HTML tag from a String
	//
	public static String StripTag ( String tag, String text )	{
		if ( (text == null) || (text.length() == 0) ) {
			return "";
		}
		
		String lcText = text.toLowerCase();
		tag = tag.toLowerCase();
	
		int tagLocation = 0;
		int tagEndLocation = 0;
		
		while ( true ) {
			tagLocation = lcText.indexOf( "<" + tag );
			
			if ( tagLocation == -1 )
				break;
			
			tagEndLocation = lcText.indexOf( ">", tagLocation ) + 1;
			
			lcText = lcText.substring(0, tagLocation) + lcText.substring(tagEndLocation);
			text = text.substring(0, tagLocation) + text.substring(tagEndLocation);
		}
		
		return escapeQuotes(text);
	}

	
	//
	// convert some HTML tags to NITF tags, and escape the
	// high ASCII characters
	//
	public static String HtmlToNitf ( String text )	{
		if ( (text == null) || (text.length() == 0) ) {
			return "";
		}
	
		// clean up the tags to make it NITF-compliant
		text = MyStringReplace(text, "<b>", "<em class=\"bold\">");
		text = MyStringReplace(text, "<i>", "<em class=\"italic\">");
		text = MyStringReplace(text, "</i>", "</em>");
		text = MyStringReplace(text, "</b>", "</em>");
		text = MyStringReplace(text, "<br>", "<br />");
		return escapeQuotes(text);
	}


	//
	// covert tab-delimited text to an HTML table
	//
	public static String TextToTable ( String text ) {
		return TextToTable( text, false );
	}
	
	public static String TextToTable ( String text, boolean firstRowHeader ) {
		String table = new String();
		String firstLine = new String();
		
		if ( firstRowHeader == true ) {
			int firstLineEnd = text.indexOf("\n");
			firstLine = text.substring(0, firstLineEnd);
			text = text.substring(firstLineEnd+1);
			firstLine = "<tr><th>" + MyStringReplace(firstLine, "\t", "</th><th>") + "</th></tr>\n";
		}
		
		text = MyStringReplace(text, "\t", "</td><td>");
		text = MyStringReplace(text, "\n", "</td></tr>\n<tr><td>");
		text = "<tr><td>" + text + "</td></tr>";
		
		if ( firstRowHeader == true ) {
			text = firstLine + text;
		}
	
		table = "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n" + text + "</table>\n\n";
	
		return table;
	}

	//
	// wrapper function for the various HTML escaping functions
	//
	public static String escapeHTML (String s) {
		return escapeHTML(s, false);
	}

	public static final String escapeHTML (String s, boolean dontConvertQuotes) {
		if ( (s == null) || (s.length() == 0) )
			return "";
		
		return escapeHtmlChars(escapeSpecialChars(escapeQuotes(s)),dontConvertQuotes);
	}

	//
	// convert special HTML characters to entities
	//
	public static String escapeHtmlChars (String s, boolean dontConvertQuotes) {
		if ( (s == null) || (s.length() == 0) )
			return "";

		StringBuffer sb = new StringBuffer();
		int n = s.length();
		
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
				case '<': sb.append("&lt;"); break;
				case '>': sb.append("&gt;"); break;
				case '&': sb.append("&amp;"); break;
				case '"':
					if ( dontConvertQuotes == true ) {
						sb.append("\"");
					} else {
						sb.append("&quot;");
					}
					break;
				default:  sb.append(c); break;
			} // switch
		} // for
		return sb.toString();
	}

	//
	// convert high ascii quotes to standard ones
	//
	public static final String escapeQuotes (String s) {
		if ( (s == null) || (s.length() == 0) )
			return "";

		//
		// this is an ugly hack, but sometimes we get escaped
		// unicode quotes
		//
		s = Helpers.MyStringReplace( s, "&#8220;", "\"" );
		s = Helpers.MyStringReplace( s, "&#8221;", "\"" );
		s = Helpers.MyStringReplace( s, "&#8216;", "'" );
		s = Helpers.MyStringReplace( s, "&#8217;", "'" );

		StringBuffer sb = new StringBuffer();
		int n = s.length();
		
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
				case 'Ò': sb.append("\""); break;
				case 'Ó': sb.append("\""); break;

				// unicode double quotes -- same as ÒÓ
				//case '\u201c': sb.append("\""); break;
				//case '\u201d': sb.append("\""); break;

				case 'Ô': sb.append("'"); break;
				case 'Õ': sb.append("'"); break;
		
				// em dash (win, mac)
				case 151: sb.append("--"); break;				
				case 209: sb.append("--"); break;				
				
				// numbers at end are 8859-1 (latin-1)
				
				// windows left curly double 
				// windows right curly double 
				case 147: sb.append("\""); break;
				case 148: sb.append("\""); break;

				// windows left curly single [ì -- //207]
				// windows right curly single [í -- //204]
				case 145: sb.append("'"); break;
				case 146: sb.append("'"); break;

				// mac left and right curly double
				//case 210: sb.append("\""); break;
				//case 211: sb.append("\""); break;
				
				// double curlys when server is a [mac 147, 148]
				case '“': sb.append("\""); break; // 236
				case '”': sb.append("\""); break; // 238
				
				// single curlys -- [mac 239, 205]
				case 'ï': sb.append("'"); break; //212
				case 'Í': sb.append("'"); break; //213
				
				// double curlys when server is a mac -- 212, 213 [241, 238]
				case 'ñ': sb.append("\""); break; //210
				case 'î': sb.append("\""); break; //211
				
				// mac left and right curly single
				//case 212: sb.append("'"); break;
				//case 213: sb.append("'"); break;


				default:  sb.append(c); break;
			} // switch
		} // for
		return sb.toString();
	}


	//
	// convert accented characters to HTML entities
	//
	public static final String escapeSpecialChars (String s) {
		if ( (s == null) || (s.length() == 0) )
			return "";

		StringBuffer sb = new StringBuffer();
		int n = s.length();
		
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
				case 'ˆ': sb.append("&agrave;");break;
				case 'Ë': sb.append("&Agrave;");break;
				case '‰': sb.append("&acirc;");break;
				case 'Š': sb.append("&auml;");break;
				case '€': sb.append("&auml;");break;
				case 'å': sb.append("&Acirc;");break;
				case 'Œ': sb.append("&aring;");break;
				case '': sb.append("&Aring;");break;
				case '¾': sb.append("&aelig;");break;
				case '®': sb.append("&AElig;");break;
				case '': sb.append("&ccedil;");break;
				case '‚': sb.append("&Ccedil;");break;
				case 'Ž': sb.append("&eacute;");break;
				case 'ƒ': sb.append("&Eacute;");break;
				case '': sb.append("&egrave;");break;
				case 'é': sb.append("&Egrave;");break;
				case '': sb.append("&ecirc;");break;
				case 'æ': sb.append("&Ecirc;");break;
				case '‘': sb.append("&euml;");break;
				case 'è': sb.append("&Euml;");break;
				case '•': sb.append("&iuml;");break;
				case 'ì': sb.append("&Iuml;");break;
				case '™': sb.append("&ocirc;");break;
				case 'ï': sb.append("&Ocirc;");break;
				case 'š': sb.append("&ouml;");break;
				case '…': sb.append("&Ouml;");break;
				case '¿': sb.append("&oslash;");break;
				case '¯': sb.append("&Oslash;");break;
				case '§': sb.append("&szlig;");break;
				case '': sb.append("&ugrave;");break;
				case 'ô': sb.append("&Ugrave;");break;         
				case 'ž': sb.append("&ucirc;");break;         
				case 'ó': sb.append("&Ucirc;");break;
				case 'Ÿ': sb.append("&uuml;");break;
				case '†': sb.append("&Uuml;");break;
				case '¨': sb.append("&reg;");break;         
				case '©': sb.append("&copy;");break;   
				case 'Û': sb.append("&euro;"); break;         
				default:  sb.append(c); break;
			} // switch
		} // for
		return sb.toString();
	}
	
	/***********************************************************************
      SHARED DIRECTACTION CODE
	 ***********************************************************************/

	//
	// get the issue specified by the date given (in the URL parameters)
	// if no date is provided, get the most recent issue
	//
    public static WOComponent issueAction(IssueReader nextPage, WORequest aRequest, EOEditingContext anEC) {

		// default to no ad banner
		MySession.setAdBanner(null);
		EOFetchSpecification spec;

		//
		// parse the URL parameters
		//
		String issueDate = (String)aRequest.formValueForKey("date");
		
		//
		// create a fetch spec to get the specified issue
		//
		if (issueDate != null) {

			//
			// create a date object
			//
			NSTimestamp theDate;
			try {
				theDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(issueDate);
			} catch (java.text.ParseException e) {
				// handle bad dates
				theDate = null;
			}
	
			NSMutableArray args = new NSMutableArray();
			args.addObject(theDate);
			EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("issue.date = %@", args);
			spec = new EOFetchSpecification("IssueFront", qual, null);

		//
		// create a fetch spec to get the most recent issue
		//
		} else {
			spec = new EOFetchSpecification("IssueFront", null, issueFrontOrdering);
		}
		
		//
		// take the fetch spec and get the results
		//
		NSArray result = anEC.objectsWithFetchSpecification(spec);
		
		//
		// create a IssueFront, assign the front, title and date to it
		//
		if ( result.count() > 0 ) {
			IssueFront myFront = (IssueFront)result.objectAtIndex(0);
			nextPage.setFront( myFront );
			MySession.setIssueDate( myFront.issue().date() );
			MySession.setPageTitle( Helpers.FullDateFormatter.format(myFront.issue().date()) );
		//
		// no issue was found, so display a warning message
		//
		} else {
			nextPage.setNoIssueFound( true );
		}
		
		
        return nextPage;
    }


	//
	// get the story specified by the id given (in the URL parameters)
	//
    public static WOComponent storyAction( StoryReader nextPage, WORequest aRequest, EOEditingContext anEC ) {

		// default to no ad banner
		MySession.setAdBanner(null);
		
		//
		// parse the URL parameters
		//
        Number storyId = Integer.valueOf( (String)aRequest.formValueForKey("storyId") );

		NSMutableArray args = new NSMutableArray();
		args.addObject(storyId);
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("id = %@", args);
		EOFetchSpecification spec = new EOFetchSpecification("Story", qual, null);

		NSArray storyList = anEC.objectsWithFetchSpecification(spec);

		if ( storyList.count() > 0 ) {

			Story myStory = (Story) storyList.objectAtIndex(0);
		
			//
			// get the assets for this story, if any
			//
			args = new NSMutableArray();
			args.addObject(myStory);
			qual = EOQualifier.qualifierWithQualifierFormat("story = %@", args);
			spec = new EOFetchSpecification("StoryAsset", qual, assetsInStoryOrdering);
	
			NSArray assetList = anEC.objectsWithFetchSpecification(spec);

			//
			// get the ad banner for this story, if any
			//
			AdCreative adBanner = new AdCreative();
			args = new NSMutableArray();
			args.addObject( myStory.section() );
			qual = EOQualifier.qualifierWithQualifierFormat("section = %@", args);
			spec = new EOFetchSpecification("AdPlacement", qual, adPlacementFreqOrdering);
			
			NSArray placementList = anEC.objectsWithFetchSpecification(spec);
			
			//
			// randomly pick an ad from the appropriate section
			//
			if ( placementList.count() > 0 ) {
				// get a random number to choose one of the banners
				Random random = new Random();
				int subtotal = 0;
				int highWaterMark = random.nextInt(100);
				boolean bannerFound = false;
				AdPlacement currentPlacement = null;
				
				for (int i=0; i < placementList.count(); i++) {
					currentPlacement = (AdPlacement)placementList.objectAtIndex(i);
					subtotal += currentPlacement.frequency().intValue();
					
					if ( subtotal > highWaterMark ) {
						adBanner = currentPlacement.adCreative();
						bannerFound = true;
						break;
					} // if
				} // for
				
				// if we didn't pick a banner
				if ( bannerFound == false ) {
					adBanner = currentPlacement.adCreative();
				}
				MySession.setAdBanner( currentPlacement.adCreative() );
			}
			
			//
			// set the page's story, asset list, title and date
			//
			nextPage.setStory( myStory );
			nextPage.setAssetList( assetList );
			MySession.setIssueDate( myStory.issue().date() );
			MySession.setPageTitle( myStory.headline() );

		//
		// no story was found, so display a warning message
		//
		} else {
			MySession.setPageTitle( "Not Found" );
			nextPage.setNoStoryFound( true );
		}
		
        return nextPage;
    }


	//
	// get the section specified by the date and section name given
	// (in the URL parameters)
	//
    public static WOComponent sectionAction( SectionReader nextPage, WORequest aRequest, EOEditingContext anEC) {

		// default to no ad banner
		MySession.setAdBanner(null);

		//
		// parse the URL parameters
		//
		String issueDate = (String)aRequest.formValueForKey("date");
		String sectionName = (String)aRequest.formValueForKey("section");

		//
		// create a date object
		//
		NSTimestamp theDate;
		try {
			theDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(issueDate);
		} catch (java.text.ParseException e) {
			// handle bad dates
			theDate = null;
		}

		//
		// create a fetch spec
		//
		NSMutableArray args = new NSMutableArray();
		args.addObject(theDate);
		args.addObject(sectionName);
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("issue.date = %@ and section.sectionName = %@", args);
		EOFetchSpecification spec = new EOFetchSpecification("SectionFront", qual, null);

		// 
		// get the results
		//
		NSArray result = anEC.objectsWithFetchSpecification(spec);
		
		//
		// create a section front and set the title and date
		//
		SectionFront mySectionFront = null;
		if (result.count() > 0) {
			mySectionFront = (SectionFront)result.objectAtIndex(0);
			nextPage.setSectionFront( mySectionFront );
			MySession.setIssueDate( mySectionFront.issue().date() );
			MySession.setPageTitle( Helpers.FullDateFormatter.format(mySectionFront.issue().date()) + Helpers.TitleSpacer() + mySectionFront.section().sectionName() );

		//
		// no section front was found, so display a warning message
		//
		} else {
			MySession.setPageTitle( Helpers.FullDateFormatter.format( theDate ) + Helpers.TitleSpacer() + "Not Found" );
			nextPage.setNoSectionFound( true );
		}

        return nextPage;
    }

	//
	// get the classified section specified by the date and section name given
	// (in the URL parameters)
	//
    public static WOComponent classifiedAdsAction (ClassifiedSectionReader nextPage, WORequest aRequest, EOEditingContext anEC) {

		//
		// parse the URL parameters
		//
		String issueDate = (String)aRequest.formValueForKey("date");
		String sectionName = (String)aRequest.formValueForKey("section");

		// Here's a simple string-to-date sequence that will work in WO4.5
		// You'll need to beef up the exception handling

		NSTimestamp theDate;
		try {
			theDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(issueDate);
		} catch (java.text.ParseException e) {
			// handle bad dates
			theDate = null;
		}

		String dateString = new NSTimestampFormatter("%Y-%m-%d").format(theDate);

		// get a bunch of classified ads
		NSMutableArray args = new NSMutableArray();
		args.addObject(theDate);

		args.addObject(sectionName);
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("issue.date = %@ and adSection.sectionName = %@", args);
		EOFetchSpecification spec = new EOFetchSpecification("AdClassified", qual, Helpers.classifiedAdsOrdering);

		NSArray result = anEC.objectsWithFetchSpecification(spec);

		nextPage.setAdList( result );
		nextPage.setAdSection( sectionName );
		
        return nextPage;
    }

	//
	// display a masthead for the issue with the given date
	//
    public static WOComponent mastheadAction(MastheadReader nextPage, WORequest aRequest, EOEditingContext anEC) {
		// default to no ad banner
		MySession.setAdBanner(null);

		//
		// parse the URL parameters
		//
		String issueDate = (String)aRequest.formValueForKey("date");

		//
		// create an NSTimestamp object
		//
		NSTimestamp theDate;
		try {
			theDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(issueDate);
		} catch (java.text.ParseException e) {
			// handle bad dates
			theDate = null;
		}

		//
		// create a fetch spec and qualifier and execute the query
		//
		NSMutableArray args = new NSMutableArray();
		args.addObject(theDate);
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("issue.date = %@", args);
		EOFetchSpecification spec = new EOFetchSpecification("IssueMasthead", qual, null);

		NSArray result = anEC.objectsWithFetchSpecification(spec);
		
		//
		// create a masthead
		//
		IssueMasthead myMasthead = null;
		if (result.count() == 1) {
			myMasthead = (IssueMasthead)result.objectAtIndex(0);
			nextPage.setMasthead( myMasthead );

		//
		// no section front was found, so display a warning message
		//
		} else {
			nextPage.setNoMastheadFound( true );
		}
		
		//
		// set the masthead title and date
		//
		MySession.setIssueDate( myMasthead.issue().date() );
		MySession.setPageTitle(  Helpers.FullDateFormatter.format(myMasthead.issue().date()) + Helpers.TitleSpacer() + "Masthead" );

        return nextPage;
    }


	//
	// show the list of classified sections
	//
    public static WOComponent classifiedsAction(Classifieds nextPage, WORequest aRequest, EOEditingContext anEC) {
		// default to no ad banner
		MySession.setAdBanner(null);

		//
		// parse the URL parameters
		//
		String issueDate = (String)aRequest.formValueForKey("date");

		//
		// create an NSTimestamp object
		//
		NSTimestamp theDate;
		try {
			theDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(issueDate);
		} catch (java.text.ParseException e) {
			// handle bad dates
			theDate = null;
		}

		MySession.setIssueDate( theDate );
		MySession.setPageTitle( Helpers.FullDateFormatter.format(theDate) + Helpers.TitleSpacer() + "Classifieds" );

        return nextPage;
	}


}
