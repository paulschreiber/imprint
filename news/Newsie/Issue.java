// Issue.java
// Created on Sun Aug 26 17:43:49 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class Issue extends EOGenericRecord {

    public Issue() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Issue(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return the date in YYYY-MM-DD, ISO format
	//
    public String IsoDate() {
		NSTimestamp ts = (NSTimestamp)storedValueForKey("date");
		String result = new NSTimestampFormatter("%Y-%m-%d").format(ts);
        return result;
    }

	//
	// return the date in YYYYMMDD format
	//
    public String compactDate() {
		NSTimestamp ts = (NSTimestamp)storedValueForKey("date");
		String result = Helpers.CompactDateFormatter.format(ts);
        return result;
    }

    public NSTimestamp date() {
        return (NSTimestamp)storedValueForKey("date");
    }

    public void setDate(NSTimestamp value) {
        takeStoredValueForKey(value, "date");
    }

    public Number volumeNumber() {
        return (Number)storedValueForKey("volumeNumber");
    }

    public void setVolumeNumber(Number value) {
        takeStoredValueForKey(value, "volumeNumber");
    }

    public Number issueNumber() {
        return (Number)storedValueForKey("issueNumber");
    }

    public void setIssueNumber(Number value) {
        takeStoredValueForKey(value, "issueNumber");
    }

    public Number id() {
        return (Number)storedValueForKey("id");
    }

    public void setId(Number value) {
        takeStoredValueForKey(value, "id");
    }

    public NSArray issuefronts() {
        return (NSArray)storedValueForKey("issuefronts");
    }

    public void setIssuefronts(NSMutableArray value) {
        takeStoredValueForKey(value, "issuefronts");
    }

    public void addToIssuefronts(IssueFront object) {
        NSMutableArray array = (NSMutableArray)issuefronts();

        willChange();
        array.addObject(object);
    }

    public void removeFromIssuefronts(IssueFront object) {
        NSMutableArray array = (NSMutableArray)issuefronts();

        willChange();
        array.removeObject(object);
    }

    public NSArray issuemastheads() {
        return (NSArray)storedValueForKey("issuemastheads");
    }

    public void setIssuemastheads(NSMutableArray value) {
        takeStoredValueForKey(value, "issuemastheads");
    }

    public void addToIssuemastheads(IssueMasthead object) {
        NSMutableArray array = (NSMutableArray)issuemastheads();

        willChange();
        array.addObject(object);
    }

    public void removeFromIssuemastheads(IssueMasthead object) {
        NSMutableArray array = (NSMutableArray)issuemastheads();

        willChange();
        array.removeObject(object);
    }

    public NSArray adclassifieds() {
        return (NSArray)storedValueForKey("adclassifieds");
    }

    public void setAdclassifieds(NSMutableArray value) {
        takeStoredValueForKey(value, "adclassifieds");
    }

    public void addToAdclassifieds(AdClassified object) {
        NSMutableArray array = (NSMutableArray)adclassifieds();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdclassifieds(AdClassified object) {
        NSMutableArray array = (NSMutableArray)adclassifieds();

        willChange();
        array.removeObject(object);
    }

    public NSArray story() {
        return (NSArray)storedValueForKey("story");
    }

    public void setStory(NSMutableArray value) {
        takeStoredValueForKey(value, "story");
    }

    public void addToStory(Story object) {
        NSMutableArray array = (NSMutableArray)story();

        willChange();
        array.addObject(object);
    }

    public void removeFromStory(Story object) {
        NSMutableArray array = (NSMutableArray)story();

        willChange();
        array.removeObject(object);
    }

    public NSArray asset() {
        return (NSArray)storedValueForKey("asset");
    }

    public void setAsset(NSMutableArray value) {
        takeStoredValueForKey(value, "asset");
    }

    public void addToAsset(Asset object) {
        NSMutableArray array = (NSMutableArray)asset();

        willChange();
        array.addObject(object);
    }

    public void removeFromAsset(Asset object) {
        NSMutableArray array = (NSMutableArray)asset();

        willChange();
        array.removeObject(object);
    }

    public NSArray adcreatives() {
        return (NSArray)storedValueForKey("adcreatives");
    }

    public void setAdcreatives(NSMutableArray value) {
        takeStoredValueForKey(value, "adcreatives");
    }

    public void addToAdcreatives(AdCreative object) {
        NSMutableArray array = (NSMutableArray)adcreatives();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdcreatives(AdCreative object) {
        NSMutableArray array = (NSMutableArray)adcreatives();

        willChange();
        array.removeObject(object);
    }
}
