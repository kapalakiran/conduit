package com.web.conduit.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.utilities.BaseFunctions;
import com.web.conduit.pages.TagsDetailPage;

public class ConduitTestCases extends BaseFunctions{

	@Test(enabled=true)
	@Parameters({"Tag"})
	public void verifySelectionOfPopularTags(String tag) {
		logInfo("To verify selected popular tag in active tab ");
		if(new TagsDetailPage(driver).verifySelectedTagFunctionalityInHomePage(tag))
			logPassed("Corresponding Selected tag values are getting populated up upon selecting a popular tag");
		else
			logFailed("Corresponding Selected tag values are not populated while selecting a popular tag ");
	}
	
	@Test(enabled=true)
	@Parameters({"Tag"})
	public void verifySelectedTagInDetailedAuthorViewAndVerifyItsOccurence(String tag) {
		logInfo("To verify selected popular tag in detailed author view screen ");
		if(new TagsDetailPage(driver).verifySelectedTagInDetailedAuthorView(tag))
			logPassed("Selected tag values are getting populated upon selecting a popular tag in detailed author view screen");
		else
			logFailed("Selected tag values are not getting populated while selecting a popular tag  in detailed author view screen");
	}
	
	@Test(enabled=true)
	@Parameters({"Tag","AdditionalTag"})
	public void verifyUpdateActiveTagFunctionality(String tag,String additionalTag) {
		logInfo("To verify update active tag functionality in detailed author view screen ");
		if(new TagsDetailPage(driver).verifyUpdateActiveTagFeatureFromTheFilter(tag, additionalTag))
			logPassed("Update Active Tag functionality is working as expected in the landing page");
		else
			logFailed("Update Active Tag functionality is not working as expected in the landing view");
	}
	
	@Test(enabled=true)
	@Parameters({"Tag"})
	public void verifySelectingATagWhichIsAlreadyActive(String tag) {
		logInfo("To verify update active tag functionality in detailed author view screen ");
		if(new TagsDetailPage(driver).verifySelectingExistingActiveTagFromTheFilter(tag))
			logPassed("Update Active Tag functionality is working as expected in the landing page");
		else
			logFailed("Update Active Tag functionality is not working as expected in the landing view");
	}
	
	@Test(enabled=true)
	@Parameters({"Tag"})
	public void verifyPopularTagCountBeforeAndAfterSelectingtheAuthor(String tag) {
		logInfo("To verify popular tag count before & after selecting the author ");
		if(new TagsDetailPage(driver).verifyPopularTagsCountBeforeAndAfterSelectingAAuthor(tag))
			logPassed("Able to verify popular tag count before & after selecting the author");
		else
			logFailed("Unable to verify popular tag count before & after selecting the author");
	}
}
