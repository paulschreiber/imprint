//
// ReaderFeedback.java: Class file for WO Component 'ReaderFeedback'
// Project Newsie
//
// Created by paul on Fri Aug 10 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class ReaderFeedback extends WOComponent {
    protected String userName;
    protected String userEmail;
    protected String userPhone;
    protected String userFeedback;
	protected boolean allDone;
    protected String userTopic;

    public ReaderFeedback(WOContext context) {
        super(context);
		allDone = false;
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

	public void setDone( boolean isDone )
	{
		this.allDone = true;
	}
}
