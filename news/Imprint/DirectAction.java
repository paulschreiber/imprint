//
// DirectAction.java
// Project Imprint
//
// Created by paul on Thu Aug 16 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;
import java.io.*;

public class DirectAction extends WODirectAction {

    public DirectAction(WORequest aRequest) {
        super(aRequest);
    }

	//
	// default to returning the most recent issue
	//
    public WOActionResults defaultAction() {
		return Helpers.issueAction(
			(IssueReader)pageWithName("IssueReader"),
			request(),
			session().defaultEditingContext()
		);
    }

	//
	// show the search form page
	//
    public WOComponent searchAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Search nextPage = (Search)pageWithName("Search");
		Session.setCompactDate( "" );
		Session.setPageTitle( "Search" );
		return nextPage;
	}

	//
	// show the about page
	//
    public WOComponent aboutAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		About nextPage = (About)pageWithName("About");
		Session.setCompactDate( "" );
		Session.setPageTitle( "About Imprint" );
		return nextPage;
	}

	//
	// show the advertiser info page
	//
    public WOComponent advertiseAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Advertise nextPage = (Advertise)pageWithName("Advertise");
		Session.setCompactDate( "" );
		Session.setPageTitle( "Advertise" );
		return nextPage;
	}

	//
	// show the contact form page
	//
    public WOComponent contactAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Contact nextPage = (Contact)pageWithName("Contact");
		Session.setCompactDate( "" );
		Session.setPageTitle( "Contact Imprint" );
		return nextPage;
	}

	//
	// handle the advertiser form feedback -- send emails,
	// display thank you message
	//
    public WOComponent advertiseFeedbackAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Advertise nextPage = (Advertise)pageWithName("Advertise");
        WORequest aRequest = request();

		/*
			"Submit a classified ad",                         0
			"Have an advertising representative contact me"   1
		*/
		
		//
		// parse the URL parameters
		//
		String userTopic = (String) aRequest.formValueForKey("TopicPopUp");
		String name = (String) aRequest.formValueForKey("name");
		String email = (String) aRequest.formValueForKey("email");
		String phoneNumber = (String) aRequest.formValueForKey("phoneNumber");
		String faxNumber = (String) aRequest.formValueForKey("faxNumber");
		String studentNumber = (String) aRequest.formValueForKey("studentNumber");
		String classifiedText = (String) aRequest.formValueForKey("classifiedText");
		String submitForm = (String)aRequest.formValueForKey("submitForm");
		String chooseTopic = (String) aRequest.formValueForKey("chooseTopic");
		
		nextPage.setSelectedTopic( userTopic );

		//
		// submit classified ad
		//
		if ( userTopic.equals("0") ) {
			nextPage.setShowUserInfo(true);
			nextPage.setShowSubmitButton(true);
			nextPage.setShowTextField(true);
			nextPage.setShowStudentNumber(true);

			//
			// send the email
			//
			if ( (submitForm != null) && (chooseTopic == null) ) {

				String messageBody = 
				"A classfied ad has been submitted.\n\n" +
				"Name: " + name + "\n" +
				"Email: " + email + "\n" +
				"Phone: " + phoneNumber + "\n" +
				"Fax: " + faxNumber + "\n" +
				"Student Number: " + studentNumber + "\n" +
				classifiedText;
			
				// email the editor
				Helpers.sendEmail(
					Helpers.ClassifiedsSender(),
					Helpers.ClassifiedsRecipient(),
					"Classified Ad request [" + email + "]",
					messageBody);
				
				nextPage.setAllDone(true);
			} // submitForm
		
		//
		// ad rep contact request
		//
		} else if ( userTopic.equals("1") ) {
			nextPage.setShowUserInfo(true);
			nextPage.setShowSubmitButton(true);
			nextPage.setShowTextField(false);
			nextPage.setShowStudentNumber(false);
			
			//
			// send the email
			//
			if ( (submitForm != null) && (chooseTopic == null) ) {
				String messageBody = 
				"An advertising request has been submitted.\n\n" +
				"Name: " + name + "\n" +
				"Email: " + email + "\n" +
				"Phone: " + phoneNumber + "\n" +
				"Fax: " + faxNumber;

				// email letters
				Helpers.sendEmail(
					Helpers.AdContactSender(),
					Helpers.AdContactRecipient(),
					"Letter to the editor ["+ name +"]",
					messageBody);
					
				nextPage.setAllDone(true);
			} //submitForm
		}
		
		return nextPage;
	}

		
	//
	// handle the contact form feedback -- send emails,
	// display thank you message
	//
    public WOComponent feedbackAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Contact nextPage = (Contact)pageWithName("Contact");
        WORequest aRequest = request();

		/*
			"Volunteer at Imprint",            0
			"Send a letter to the editor",     1
			"Submit a correction request",     2
			"Submit an article"                3
		*/
		
		//
		// parse the URL parameters
		//
		String userTopic = (String) aRequest.formValueForKey("TopicPopUp");
		String userName = (String) aRequest.formValueForKey("userName");
		String userEmail = (String) aRequest.formValueForKey("userEmail");
		String userPhone = (String) aRequest.formValueForKey("userPhone");
		String userFeedback = (String) aRequest.formValueForKey("userFeedback");
		String year = (String) aRequest.formValueForKey("year");
		String program = (String) aRequest.formValueForKey("program");
		String articleDate = (String) aRequest.formValueForKey("articleDate");
		String articleTitle = (String) aRequest.formValueForKey("articleTitle");
		String submitForm = (String) aRequest.formValueForKey("submitForm");
		String chooseTopic = (String) aRequest.formValueForKey("chooseTopic");
		String aFileName = (String) aRequest.formValueForKey( "thefile.filename" );

		NSArray interestsList = aRequest.formValuesForKey("interests");

		//
		// fill in the form with the values the user entered
		// this way they don't have to reenter info if we show
		// them the form again due to an error/incomplete information
		//
		nextPage.setSelectedTopic( userTopic );
		nextPage.setUserName( userName );
		nextPage.setUserEmail( userEmail );
		nextPage.setUserPhone( userPhone );
		nextPage.setUserFeedback( userFeedback );
		nextPage.setYear( year );
		nextPage.setProgram( program );
		nextPage.setArticleDate( articleDate );
		nextPage.setArticleTitle( articleTitle );

		if ( (interestsList != null) && (interestsList.count() > 0) ) {
			if ( interestsList.containsObject( "arts" ) )
				nextPage.setArtsChecked( "arts" );
				
			if ( interestsList.containsObject( "features" ) )
				nextPage.setFeaturesChecked("features");
				
			if ( interestsList.containsObject( "forum" ) )
				nextPage.setForumChecked("forum");
				
			if ( interestsList.containsObject( "news" ) )
				nextPage.setNewsChecked("news");
				
			if ( interestsList.containsObject( "science" ) )
				nextPage.setScienceChecked("science");
				
			if ( interestsList.containsObject( "sports" ) )
				nextPage.setSportsChecked("sports");
				
			if ( interestsList.containsObject( "web" ) )
				nextPage.setWebChecked("web");
	
			if ( interestsList.containsObject( "graphics" ) )
				nextPage.setGraphicsChecked("graphics");
	
			if ( interestsList.containsObject( "photo" ) )
				nextPage.setPhotoChecked("photo");
	
			if ( interestsList.containsObject( "sysadmin" ) )
				nextPage.setSysAdminChecked("sysadmin");
		}

		//
		// ensure the user fills in the minimal form requirements
		// name, email, phone number
		//
		if ( (submitForm != null) && (chooseTopic == null) && (userTopic != null) ) {

			// new volunteer sign up
			if ( userTopic.equals("0") ) {
				if ( (userName.length() == 0) ||
						(userEmail.length() == 0) || (userPhone.length() == 0) ) {
					nextPage.setShowInfoWarning( true );
					
					// set submitForm to null so the mail doesn't get sent
					submitForm = null;
				}
			
			// letter to the editor
			} else if ( userTopic.equals("1") ) {
				if ( (userName.length() == 0) || (userEmail.length() == 0) ||
						(userPhone.length() == 0)) {
					nextPage.setShowInfoWarning( true );
					submitForm = null;
				}
				
				if ( (year.length() == 0) || (program.length() == 0) ||
						(userFeedback.length() == 0) ) {
					nextPage.setShowLetterWarning( true );
					submitForm = null;
				}
			
			
			// correction request
			} else if ( userTopic.equals("2") ) {
				if ( (userName.length() == 0) || (userEmail.length() == 0) ||
						(userPhone.length() == 0)) {
					nextPage.setShowInfoWarning( true );
					submitForm = null;
				}
				
				if ( (articleDate.length() == 0) || (articleTitle.length() == 0) ||
						(userFeedback.length() == 0) ) {
					nextPage.setShowCorrectionWarning( true );
					submitForm = null;
				}
			
			// submit article request
			} else if ( userTopic.equals("3") ) {
				if ( (userName.length() == 0) || (userEmail.length() == 0) ||
						(userPhone.length() == 0)) {
					nextPage.setShowInfoWarning( true );
					submitForm = null;
				}
				
				if ( (aFileName.length() == 0) && (userFeedback.length() == 0) ) {
					nextPage.setShowArticleWarning( true );
					submitForm = null;
				}
			}
			
			// if there is at least one error, set the error flag
			if ( submitForm == null ) {
				if ( (nextPage.showInfoWarning() == true) ||
						(nextPage.showLetterWarning() == true ) ||
						(nextPage.showArticleWarning() == true ) ||
						(nextPage.showCorrectionWarning() == true ) ) {
					nextPage.setAtLeastOneWarning( true );
				}
			}
			
		} // submitForm && chooseTopic


		if ( userTopic != null ) {
			//
			// new volunteers
			//
			if ( userTopic.equals("0") ) {
				nextPage.setShowInterestCheckboxes(true);
				nextPage.setShowUserInfo(true);
				nextPage.setShowSubmitButton(true);
				nextPage.setShowTextField(false);
				nextPage.setShowYearProgram(false);
				nextPage.setShowCorrectionInfo(false);
				nextPage.setShowFileUpload(false);
			
				//
				// send the email
				//
				if ( (submitForm != null) && (chooseTopic == null) ) {
					String interestsString = interestsList.componentsJoinedByString(", ");
	
					String messageBody = 
					"A new volunteer has signed up.\n\n" +
					"Name: " + userName + "\n" +
					"Email: " + userEmail + "\n" +
					"Phone: " + userPhone + "\n" +
					"Interests: " + interestsString;
				
					// email the editor
					Helpers.sendEmail(
						Helpers.FeedbackSender(),
						Helpers.FeedbackRecipient(),
						"New Volunteer Signup",
						messageBody);
	
					// sub to all list
					Helpers.mailingListSubscribe( "all", userEmail );
	
					// sub to section lists
					for (int i=0; i < interestsList.count(); i++) {
						String currentInterest = (String) interestsList.objectAtIndex(i);
						String listName = (String) Helpers.mailingListsDict.objectForKey(currentInterest);
						if ( listName != null ) {
							Helpers.mailingListSubscribe( listName, userEmail );
						} // if
					}
					
					nextPage.setAllDone(true);
				} // submitForm
			
			//
			// letter to the editor
			//
			} else if ( userTopic.equals("1") ) {
				nextPage.setShowInterestCheckboxes(false);
				nextPage.setShowUserInfo(true);
				nextPage.setShowSubmitButton(true);
				nextPage.setShowTextField(true);
				nextPage.setShowYearProgram(true);
				nextPage.setShowCorrectionInfo(false);
				nextPage.setShowFileUpload(false);
	
				//
				// send the email
				//
				if ( (submitForm != null) && (chooseTopic == null) ) {
					String messageBody = 
					"A letter to the editor has been submitted.\n\n" +
					"Name: " + userName + "\n" +
					"Email: " + userEmail + "\n" +
					"Phone: " + userPhone + "\n" +
					"Program: " + program + "\n" +
					"Year: " + year + "\n\n" +
					userFeedback;
	
					// email letters
					Helpers.sendEmail(
						Helpers.FeedbackSender(),
						Helpers.LettersRecipient(),
						"Letter to the Editor ["+userName+"]",
						messageBody);
						
					nextPage.setAllDone(true);
				} //submitForm
				
			//
			// correction
			//
			} else if ( userTopic.equals("2") ) {
				nextPage.setShowInterestCheckboxes(false);
				nextPage.setShowUserInfo(true);
				nextPage.setShowSubmitButton(true);
				nextPage.setShowTextField(true);
				nextPage.setShowYearProgram(false);
				nextPage.setShowCorrectionInfo(true);
				nextPage.setShowFileUpload(false);
	
				//
				// send the email
				//
				if ( (submitForm != null) && (chooseTopic == null) ) {
					String messageBody = 
					"A correction request has been submitted.\n\n" +
					"Name: " + userName + "\n" +
					"Email: " + userEmail + "\n" +
					"Article date: " + articleDate + "\n" +
					"Article title: " + articleTitle + "\n" +
					userFeedback;
	
					// email letters
					Helpers.sendEmail(
						Helpers.FeedbackSender(),
						Helpers.LettersRecipient(),
						"Correction request ["+userName+"]",
						messageBody);
	
					nextPage.setAllDone(true);
				} //submitForm
			
			//
			// submit article
			//
			} else if ( userTopic.equals("3") ) {
				nextPage.setShowInterestCheckboxes(false);
				nextPage.setShowUserInfo(true);
				nextPage.setShowSubmitButton(true);
				nextPage.setShowTextField(true);
				nextPage.setShowYearProgram(false);
				nextPage.setShowCorrectionInfo(false);
				nextPage.setShowFileUpload(true);
	
				//
				// send the email
				//
				if ( (submitForm != null) && (chooseTopic == null) ) {
								
					NSArray someFile = (NSArray) request().formValuesForKey( "thefile" );
					NSData aFileContents = (NSData)someFile.objectAtIndex( 0 );
					String fileName = Helpers.lastPathComponent( aFileName );
					String filePath = new String();
	
					String messageBody = 
					"An article has been submitted.\n\n" +
					"Name: " + userName + "\n" +
					"Email: " + userEmail + "\n" +
					"File name: " + fileName + "\n" +
					"Contents:\n\n" + userFeedback;
	
					// 
					// send email with attachment, if a file was specified
					//
					if ( aFileName != null ) {
	
						try {
							File tmpf = File.createTempFile("/tmp", null);
							tmpf.deleteOnExit();
							filePath = tmpf.getAbsolutePath();
							aFileContents.writeToFile( filePath );
						} catch (IOException e) {
							// should not happen
							System.out.println("Can't create temp file for emailing. file name:" + fileName);
						}
	
						Helpers.sendEmailWithAttachment(
							Helpers.FeedbackSender(),
							Helpers.FeedbackRecipient(),
							"Article Submission ["+userName+"]",
							messageBody,
							filePath,
							fileName);
					
					//
					// send email without attachment
					//
					} else {
						Helpers.sendEmail(
							Helpers.FeedbackSender(),
							Helpers.FeedbackRecipient(),
							"Article Submission ["+userName+"]",
							messageBody);
					}
						
					nextPage.setAllDone(true);
				} //submitForm
			} // topic == 3
		} // userTopic != null
		
		return nextPage;
	}

	//
	// display a list of issues
	//
    public WOComponent archiveAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Archive nextPage = (Archive)pageWithName("Archive");
        WORequest aRequest = request();
		WODisplayGroup displayGroup;
		
		displayGroup = nextPage.displayGroup();
		
		//
		// parse the URL parameters, set the current batch
		//
        Number aBatch = Helpers.numericFormValueFromRequest( aRequest, "batchIndex" );
        int aBatchIndex = (aBatch == null) ? 1 : aBatch.intValue();
		int maxBatch = displayGroup.batchCount();

		if (aBatchIndex < 1) {
			aBatchIndex = maxBatch;
		}

		displayGroup.setCurrentBatchIndex( aBatchIndex );

		//
		// hide the section nav bar, set the title
		//
		Session.setCompactDate( "" );
		Session.setPageTitle( "Archives" );

		return nextPage;
	}

	//
	// display a list articles matching the criteria
	//
    public WOComponent searchResultsAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		Search nextPage = (Search)pageWithName("Search");

		WORequest aRequest = request();
		WODisplayGroup displayGroup = nextPage.displayGroup();
		NSMutableArray args = new NSMutableArray();
		NSMutableArray qualifierList = new NSMutableArray();
		EOQualifier searchQualifier;
		String qualifierString = "";
		String headline;
		String body;
		String author;

		//
		// variables to handle the date from the popup buttons
		//
		int monthIndex, yearIndex, dayIndex;
		String monthValue, yearValue, dayValue;
		NSTimestamp startDate, endDate;
		String startDateString, endDateString;
		
		//
		// handle batch counting
		//
		int currentBatch;
		int previousBatch;
		Number currentBatchRaw;

		//
		// headline -- get the value, add it to the query if it's
		// not null; set the value of the text field
		//
		headline = (String) aRequest.formValueForKey("headline");
		if ( headline != null && headline.length() > 0 ) {
			nextPage.setHeadline( headline );
			args.addObject(headline);
			qualifierList.addObject(" headline contains %@ ");
		}

		//
		// body -- get the value, add it to the query if it's
		// not null; set the value of the text field
		//
		body = (String) aRequest.formValueForKey("body");
		if ( body != null && body.length() > 0 ) {
			nextPage.setBody( body );
			args.addObject(body);
			qualifierList.addObject(" contents contains %@ ");
		}

		//
		// author -- get the value, add it to the query if it's
		// not null; set the value of the text field
		//
		author = (String) aRequest.formValueForKey("author");
		if ( author != null && author.length() > 0 ) {
			nextPage.setAuthor( author );
			args.addObject(author);
			args.addObject(author);
			args.addObject(author);
			qualifierList.addObject(" ( author.preferredName contains %@ or author2.preferredName contains %@ or author3.preferredName contains %@ ) ");
		}

		//
		// set the state of popup menus
		//
		nextPage.setStartMonth( (String) aRequest.formValueForKey("StartMonth") );
		nextPage.setStartDay( (String) aRequest.formValueForKey("StartDay") );
		nextPage.setStartYear( (String) aRequest.formValueForKey("StartYear") );

		nextPage.setEndMonth( (String) aRequest.formValueForKey("EndMonth") );
		nextPage.setEndDay( (String) aRequest.formValueForKey("EndDay") );
		nextPage.setEndYear( (String) aRequest.formValueForKey("EndYear") );

		//
		// parse the start date from popup menus into an NSTimestamp
		//
		try {
			monthIndex = Integer.parseInt( (String) aRequest.formValueForKey("StartMonth") );
			dayIndex = Integer.parseInt( (String) aRequest.formValueForKey("StartDay") );
			yearIndex = Integer.parseInt( (String) aRequest.formValueForKey("StartYear") );
			monthValue = (String)Helpers.numericMonthList.objectAtIndex( monthIndex );
			dayValue = (String)Helpers.paddedDayList.objectAtIndex( dayIndex );
			yearValue = (String)Helpers.yearList().objectAtIndex( yearIndex ).toString();
	
			startDateString = yearValue + monthValue + dayValue;
			startDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(startDateString);
		} catch (java.lang.NullPointerException e) {
			startDate = null;
		} catch (java.text.ParseException e) {
			startDate = null;
		} catch (java.lang.NumberFormatException e) {
			startDate = null;
		}

		//
		// parse the end date from popup menus into an NSTimestamp
		//
		try {
			monthIndex = Integer.parseInt( (String) aRequest.formValueForKey("EndMonth") );
			dayIndex = Integer.parseInt( (String) aRequest.formValueForKey("EndDay") );
			yearIndex = Integer.parseInt( (String) aRequest.formValueForKey("EndYear") );
			monthValue = (String)Helpers.numericMonthList.objectAtIndex( monthIndex );
			dayValue = (String)Helpers.paddedDayList.objectAtIndex( dayIndex );
			yearValue = (String)Helpers.yearList().objectAtIndex( yearIndex ).toString();
	
			endDateString = yearValue + monthValue + dayValue;
			endDate = (NSTimestamp)Helpers.CompactDateFormatter.parseObject(endDateString);
		
		} catch (java.lang.NullPointerException e ) {
			endDate = null;
		} catch (java.text.ParseException e) {
			endDate = null;
		} catch (java.lang.NumberFormatException e) {
			endDate = null;
		}

		//
		// set the query if both dates are present
		//
		if ( startDate != null && endDate != null ) {
			args.addObject(startDate);
			args.addObject(endDate);
			qualifierList.addObject(" issue.date >= %@ ");
			qualifierList.addObject(" issue.date <= %@ ");

		//
		// set the query if only the start date is present
		//
		} else if ( startDate != null ) {
			args.addObject(startDate);
			qualifierList.addObject(" issue.date >= %@ ");		

		//
		// set the query if only the end date is present
		//
		} else if ( endDate != null ) {
			args.addObject(endDate);
			qualifierList.addObject(" issue.date <= %@ ");		
		}


		//
		// create the qualifier and execute the query
		//
		qualifierString = qualifierList.componentsJoinedByString(" and ");
		searchQualifier = EOQualifier.qualifierWithQualifierFormat(qualifierString, args);
		displayGroup.setQualifier( searchQualifier );

		displayGroup.fetch();

		//
		// set the correct batch, defaulting to batch 1
		//
		currentBatchRaw = Helpers.numericFormValueFromRequest( aRequest, "batch" );
        currentBatch = (currentBatchRaw == null) ? 1 : currentBatchRaw.intValue();

		displayGroup.setCurrentBatchIndex( currentBatch );
		
		
		if ( displayGroup.hasMultipleBatches() ) {
			if ( currentBatch > 1 ) {
				previousBatch = currentBatch - 1;
			} else {
				previousBatch = 1;
			}
		
			nextPage.setNextBatch( currentBatch + 1 );
			nextPage.setPreviousBatch( previousBatch );
		}
		
		nextPage.setDisplayGroup( displayGroup );
		Session.setCompactDate( "" );
		Session.setPageTitle( "Search Results" );

		return nextPage;
	}
	

	//
	// get the story specified by the id given (in the URL parameters)
	// but return the NITF XML version of the page
	//
    public WOComponent nitfAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		StoryReaderNITF nextPage = (StoryReaderNITF)pageWithName("StoryReaderNITF");

		WORequest aRequest = request();
		EOEditingContext anEC = session().defaultEditingContext();

		//
		// parse the URL parameters
		//
        Number storyId = Integer.valueOf( (String)aRequest.formValueForKey("storyId") );
		
		//
		// grab the story -- i'm not doing error hanging here,
		// nobody should ever get a wrong nitf? URL without
		// messing around
		//
		Story myStory = (Story)EOUtilities.objectWithPrimaryKeyValue( anEC, "Story", storyId );

		// clean up the text
		myStory.setContents( Helpers.HtmlToNitf( myStory.contents() ) );

		nextPage.setStory( myStory );
        return nextPage;
	}

	//
	// get the issue specified by the date given
	// (in the URL parameters); but return the text-only version of the page
	//
	// unlike issueAction, we don't display an IssueFront object; we just verify its
	// existence to ensure this issue has been published
	//
	//
    public WOComponent liteIssueAction() {
		IssueReaderLite nextPage = (IssueReaderLite)pageWithName("IssueReaderLite");
		
		EOFetchSpecification spec;
		EOEditingContext anEC = session().defaultEditingContext();
		NSTimestamp theDate = null;
		WORequest aRequest = request();
		
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
			spec = new EOFetchSpecification("IssueFront", null, Helpers.issueFrontOrdering);
		}
		
		//
		// take the fetch spec and get the results
		//
		NSArray result = anEC.objectsWithFetchSpecification(spec);
		
		
		//
		// assign the title title and date to the page
		//
		if ( result.count() > 0 ) {
			IssueFront myFront = (IssueFront)result.objectAtIndex(0);
			
			// clean up the text for lite
			String content = myFront.content();
			content = Helpers.StripTableTags( Helpers.StripGraphicTags( content ) );
			content = Helpers.MyStringReplace( content, Helpers.BaseStoryUrl(), Helpers.BaseStoryLiteUrl() );
			myFront.setContent( content );
			
			
			nextPage.setFront( myFront );
			Session.setIssueDate( theDate );
			Session.setPageTitle( Helpers.FullDateFormatter.format(theDate) );
			nextPage.setDate( theDate );
		//
		// no issue was found, so display a warning message
		//
		} else {
			nextPage.setNoIssueFound( true );
		}

		
		
		return nextPage;
	}


	//
	// get the section specified by the date and section name given
	// (in the URL parameters); but return the text-only version of the page
	//
    public WOComponent liteSectionAction() {
		SectionReaderLite nextPage = (SectionReaderLite)pageWithName("SectionReaderLite");
		
		WORequest aRequest = request();
		EOEditingContext anEC = session().defaultEditingContext();
		NSMutableArray storyList = new NSMutableArray();
		NSMutableArray letterList = new NSMutableArray();
		
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

		Section sectionItem = new Section();
		sectionItem.setSectionName( sectionName );
		NSMutableArray result = new NSMutableArray();
		result = Helpers.GetSectionStories( sectionItem, theDate, anEC );		
		storyList = (NSMutableArray) result.objectAtIndex(0);
		letterList = (NSMutableArray) result.objectAtIndex(1);
		
		if ( storyList != null ) {
			nextPage.setStoryList( storyList );
			nextPage.setLetterList( letterList );
			nextPage.setSectionName( sectionName );
			nextPage.setDate( theDate );
			Session.setIssueDate( theDate );
			Session.setPageTitle( Helpers.FullDateFormatter.format(theDate) + Helpers.TitleSpacer() + sectionName );
		} else {
			nextPage.setNoSectionFound( true );
		}
		
		return nextPage;
	}

	//
	// get the story specified by the id given (in the URL parameters)
	// but return the text-only version of the page
	//
    public WOComponent liteAction() {
		// default to no ad banner
		MySession.setAdBanner(null);

		StoryReaderLite nextPage = (StoryReaderLite)pageWithName("StoryReaderLite");
		
		WORequest aRequest = request();
		EOEditingContext anEC = session().defaultEditingContext();

		//
		// parse the URL parameters
		//
        Number storyId = Integer.valueOf( (String)aRequest.formValueForKey("storyId") );

		//
		// grab the story
		//
		NSMutableArray args = new NSMutableArray();
		args.addObject(storyId);
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat("id = %@", args);
		EOFetchSpecification spec = new EOFetchSpecification("Story", qual, null);

		NSArray storyList = anEC.objectsWithFetchSpecification(spec);

		if ( storyList.count() > 0 ) {

			Story myStory = (Story) storyList.objectAtIndex(0);
			
			// pull out the graphics in the story
			myStory.setContents( Helpers.StripGraphicTags ( myStory.contents() ) );

			nextPage.setStory( myStory );
			Session.setIssueDate( myStory.issue().date() );
			Session.setPageTitle( myStory.headline() );

		//
		// no story was found, so display a warning message
		//
		} else {
			nextPage.setNoStoryFound( true );
		}
		
        return nextPage;
    }

	//
	// these story/section/issue/classified viewers are shared
	// see Helpers.java
	//

	// cheesy wrapper for storyAction so we can refer to it
	// by a second name
    public WOComponent sAction() {
		return storyAction();
	}
	
    public WOComponent storyAction() {
		return Helpers.storyAction(
				(StoryReader)pageWithName("StoryReader"),
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
