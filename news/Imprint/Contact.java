//
// Contact.java: Class file for WO Component 'Contact'
// Project Imprint
//
// Created by paul on Fri Aug 31 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Contact extends WOComponent {
    protected String selectedTopic;
    protected String userName;
    protected String userEmail;
    protected String userPhone;
    protected String userFeedback;
    protected String userTopic;
    protected String program;
    protected String year;
    protected String articleTitle;
    protected String articleDate;

	// for conditionals
	protected boolean allDone;
    protected boolean showInterestCheckboxes;
    protected boolean showTextField;
    protected boolean showSubmitButton;
    protected boolean showUserInfo;
    protected boolean showYearProgram;
    protected boolean showCorrectionInfo;
    protected boolean showFileUpload;
	
	// for error messages
    protected boolean showInfoWarning;
    protected boolean showArticleWarning;
    protected boolean showCorrectionWarning;
    protected boolean showLetterWarning;
    protected boolean atLeastOneWarning;

	// for volunteer checkboxes
    protected String artsChecked;
    protected String featuresChecked;
    protected String forumChecked;
    protected String newsChecked;
    protected String scienceChecked;
    protected String sportsChecked;
    protected String webChecked;
    protected String graphicsChecked;
    protected String photoChecked;
    protected String sysAdminChecked;

    protected String attachmentLocation;
    protected NSData attachmentData;

    public Contact(WOContext context) {
        super(context);
		allDone = false;
		showInterestCheckboxes = false;
		showTextField = false;
		showSubmitButton = false;
		showUserInfo = false;
    }

    public WOComponent ShowTopicOptions()
    {
        return null;
    }

    public WOComponent SubmitForm()
    {
        return null;
    }

	public NSArray feedbackList()
	{
		return Helpers.feedbackList;
	}
	

    public String selectedTopic()
    {
        return selectedTopic;
    }
    public void setSelectedTopic(String newSelectedTopic)
    {
        selectedTopic = newSelectedTopic;
    }

    public String userName()
    {
        return userName;
    }
    public void setUserName(String newUserName)
    {
        userName = newUserName;
    }

    public String userEmail()
    {
        return userEmail;
    }
    public void setUserEmail(String newUserEmail)
    {
        userEmail = newUserEmail;
    }

    public String userPhone()
    {
        return userPhone;
    }
    public void setUserPhone(String newUserPhone)
    {
        userPhone = newUserPhone;
    }

    public String userFeedback()
    {
        return userFeedback;
    }
    public void setUserFeedback(String newUserFeedback)
    {
        userFeedback = newUserFeedback;
    }

    public String userTopic()
    {
        return userTopic;
    }
    public void setUserTopic(String newUserTopic)
    {
        userTopic = newUserTopic;
    }

	public void setAllDone( boolean isDone )
	{
		this.allDone = true;
	}
	
    public boolean showInterestCheckboxes()
    {
        return showInterestCheckboxes;
    }
    public void setShowInterestCheckboxes(boolean newShowInterestCheckboxes)
    {
        showInterestCheckboxes = newShowInterestCheckboxes;
    }

    public boolean showTextField()
    {
        return showTextField;
    }
    public void setShowTextField(boolean newShowTextField)
    {
        showTextField = newShowTextField;
    }

    public boolean showSubmitButton()
    {
        return showSubmitButton;
    }
    public void setShowSubmitButton(boolean newShowSubmitButton)
    {
        showSubmitButton = newShowSubmitButton;
    }

    public boolean showUserInfo()
    {
        return showUserInfo;
    }
    public void setShowUserInfo(boolean newShowUserInfo)
    {
        showUserInfo = newShowUserInfo;
    }

    public String artsChecked()
    {
        return artsChecked;
    }
    public void setArtsChecked(String newArtsChecked)
    {
        artsChecked = newArtsChecked;
    }

    public String featuresChecked()
    {
        return featuresChecked;
    }
    public void setFeaturesChecked(String newFeaturesChecked)
    {
        featuresChecked = newFeaturesChecked;
    }

    public String forumChecked()
    {
        return forumChecked;
    }
    public void setForumChecked(String newForumChecked)
    {
        forumChecked = newForumChecked;
    }

    public String newsChecked()
    {
        return newsChecked;
    }
    public void setNewsChecked(String newNewsChecked)
    {
        newsChecked = newNewsChecked;
    }

    public String scienceChecked()
    {
        return scienceChecked;
    }
    public void setScienceChecked(String newScienceChecked)
    {
        scienceChecked = newScienceChecked;
    }

    public String sportsChecked()
    {
        return sportsChecked;
    }
    public void setSportsChecked(String newSportsChecked)
    {
        sportsChecked = newSportsChecked;
    }

    public String webChecked()
    {
        return webChecked;
    }
    public void setWebChecked(String newWebChecked)
    {
        webChecked = newWebChecked;
    }

    public String graphicsChecked()
    {
        return graphicsChecked;
    }
    public void setGraphicsChecked(String newGraphicsChecked)
    {
        graphicsChecked = newGraphicsChecked;
    }

    public String photoChecked()
    {
        return photoChecked;
    }
    public void setPhotoChecked(String newPhotoChecked)
    {
        photoChecked = newPhotoChecked;
    }

    public String sysAdminChecked()
    {
        return sysAdminChecked;
    }
    public void setSysAdminChecked(String newSysAdminChecked)
    {
        sysAdminChecked = newSysAdminChecked;
    }

    public String program()
    {
        return program;
    }
    public void setProgram(String newProgram)
    {
        program = newProgram;
    }

    public String year()
    {
        return year;
    }
    public void setYear(String newYear)
    {
        year = newYear;
    }

    public boolean showYearProgram()
    {
        return showYearProgram;
    }
    public void setShowYearProgram(boolean newShowYearProgram)
    {
        showYearProgram = newShowYearProgram;
    }

    public String articleTitle()
    {
        return articleTitle;
    }
    public void setArticleTitle(String newArticleTitle)
    {
        articleTitle = newArticleTitle;
    }

    public String articleDate()
    {
        return articleDate;
    }
    public void setArticleDate(String newArticleDate)
    {
        articleDate = newArticleDate;
    }

    public boolean showCorrectionInfo()
    {
        return showCorrectionInfo;
    }
    public void setShowCorrectionInfo(boolean newShowCorrectionInfo)
    {
        showCorrectionInfo = newShowCorrectionInfo;
    }

    public String attachmentLocation()
    {
        return attachmentLocation;
    }
    public void setAttachmentLocation(String newAttachmentLocation)
    {
        attachmentLocation = newAttachmentLocation;
    }

    public NSData attachmentData()
    {
        return attachmentData;
    }
    public void setAttachmentData(NSData newAttachmentData)
    {
        attachmentData = newAttachmentData;
    }

    public boolean showFileUpload()
    {
        return showFileUpload;
    }
    public void setShowFileUpload(boolean newShowFileUpload)
    {
        showFileUpload = newShowFileUpload;
    }

    public boolean showInfoWarning()
    {
        return showInfoWarning;
    }
    public void setShowInfoWarning(boolean newShowInfoWarning)
    {
        showInfoWarning = newShowInfoWarning;
    }

    public boolean showArticleWarning()
    {
        return showArticleWarning;
    }
    public void setShowArticleWarning(boolean newShowArticleWarning)
    {
        showArticleWarning = newShowArticleWarning;
    }

    public boolean showCorrectionWarning()
    {
        return showCorrectionWarning;
    }
    public void setShowCorrectionWarning(boolean newShowCorrectionWarning)
    {
        showCorrectionWarning = newShowCorrectionWarning;
    }

    public boolean showLetterWarning()
    {
        return showLetterWarning;
    }
    public void setShowLetterWarning(boolean newShowLetterWarning)
    {
        showLetterWarning = newShowLetterWarning;
    }

    public boolean atLeastOneWarning()
    {
        return atLeastOneWarning;
    }
    public void setAtLeastOneWarning(boolean newAtLeastOneWarning)
    {
        atLeastOneWarning = newAtLeastOneWarning;
    }

}
