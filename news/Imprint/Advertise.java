//
// Advertise.java: Class file for WO Component 'Advertise'
// Project Imprint
//
// Created by paul on Fri Aug 31 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Advertise extends WOComponent {
    protected String selectedTopic;
    protected String studentNumber;
    protected String faxNumber;
    protected String phoneNumber;
    protected String email;
    protected String name;
    protected String classifiedText;
	
    protected boolean allDone;
	protected boolean showUserInfo;
	protected boolean showTextField;
    protected boolean showStudentNumber;
    protected boolean showSubmitButton;

    public Advertise(WOContext context) {
        super(context);
		allDone = false;
		showUserInfo = false;
		showTextField = false;
		showStudentNumber = false;
		showSubmitButton = false;
    }

	public NSArray adInfoList()
	{
		return Helpers.adInfoList;
	}

    public boolean allDone()
    {
        return allDone;
    }
    public void setAllDone(boolean newAllDone)
    {
        allDone = newAllDone;
    }

    public boolean showTextField()
    {
        return showTextField;
    }
    public void setShowTextField(boolean newShowTextField)
    {
        showTextField = newShowTextField;
    }

    public boolean showUserInfo()
    {
        return showUserInfo;
    }
    public void setShowUserInfo(boolean newShowUserInfo)
    {
        showUserInfo = newShowUserInfo;
    }

    public boolean showSubmitButton()
    {
        return showSubmitButton;
    }
    public void setShowSubmitButton(boolean newShowSubmitButton)
    {
        showSubmitButton = newShowSubmitButton;
    }

    public WOComponent ShowTopicOptions()
    {
        return null;
    }

    public WOComponent SubmitForm()
    {
		return this;
    }

    public String selectedTopic()
    {
        return selectedTopic;
    }
    public void setSelectedTopic(String newSelectedTopic)
    {
        selectedTopic = newSelectedTopic;
    }

    public String studentNumber()
    {
        return studentNumber;
    }
    public void setStudentNumber(String newStudentNumber)
    {
        studentNumber = newStudentNumber;
    }

    public String faxNumber()
    {
        return faxNumber;
    }
    public void setFaxNumber(String newFaxNumber)
    {
        faxNumber = newFaxNumber;
    }

    public String phoneNumber()
    {
        return phoneNumber;
    }
    public void setPhoneNumber(String newPhoneNumber)
    {
        phoneNumber = newPhoneNumber;
    }

    public String email()
    {
        return email;
    }
    public void setEmail(String newEmail)
    {
        email = newEmail;
    }

    public String classifiedText()
    {
        return classifiedText;
    }
    public void setClassifiedText(String newClassifiedText)
    {
        classifiedText = newClassifiedText;
    }
	
    public String name()
    {
        return name;
    }
    public void setName(String newName)
    {
        name = newName;
    }

    public boolean showStudentNumber()
    {
        return showStudentNumber;
    }
    public void setShowStudentNumber(boolean newShowStudentNumber)
    {
        showStudentNumber = newShowStudentNumber;
    }

}
