//
// MySession.java
// Project Newsie
//
// Created by paul on Sat Sep 15 2001
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

// dummy wrapper class to work around namesapce conflicts
// on the Session (extends WOSession) and Session 
// (javax....)

public class MySession extends Session {}
