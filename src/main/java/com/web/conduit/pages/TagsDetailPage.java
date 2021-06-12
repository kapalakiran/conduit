package com.web.conduit.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilities.BaseFunctions;

public class TagsDetailPage extends BaseFunctions{

	@FindBy(css="a[class*='tag-default']")
	private List<WebElement> popularTags;

	@FindBy(css="a[class*='nav-link active ng-binding']")
	private WebElement activeLinkBtn;

	@FindBy(css = "a[class*='author ng-binding']")
	private List<WebElement> authorLinks;

	@FindBy(css="div.articles-toggle li.nav-item")
	private List<WebElement> articleNavigationBtns;

	public TagsDetailPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author kirankumar
	 * @description - to select a tag & count the no. of select tag occurrence
	 * @param sampleTag
	 * @return
	 */
	public Boolean verifySelectedTagFunctionalityInHomePage(String sampleTag) {
		Boolean status = false;
		try {
			selectValueFromListOfWebElements(popularTags, sampleTag);
			waitUntilElementFound(getWebElement(sampleTag).get(0));
			int actualSizeOfReqTags = getWebElement(sampleTag).size();
			if(actualSizeOfReqTags==10) {
				logPassed("Selected Tag values are getting displayed in active tab"+" Expected count - 20 "+"Actual Count "+ - actualSizeOfReqTags+"");
				status = true;
			}else
				logFailed("Selected tag values are not getting displayed in active tab"+" Expected count - 20 "+"Actual Count "+ - actualSizeOfReqTags+"");

		}catch (Exception e) {
			logException(e);
		}
		return status;
	}

	public List<WebElement> getWebElement(String text) {
		return driver.findElements(By.xpath("//li[contains(text(),'"+text+"')]"));
	}

	/**
	 * @author kirankumar
	 * @description - to select the navigation buttons & verify its respective tags
	 * @param sampleTag
	 * @return
	 */
	public Boolean verifySelectedTagInDetailedAuthorView(String sampleTag) {
		Boolean status = false;
		try {
			selectValueFromListOfWebElements(popularTags, sampleTag);
			logInfo("Selected sample tag - "+ sampleTag);
			waitUntilElementFound(authorLinks.get(0));
			click(authorLinks.get(0),"Author");
			waitUntilElementFound(articleNavigationBtns.get(0));
			for(int i=0;i<articleNavigationBtns.size();i++) {
				waitUntilElementFound(getWebElement(sampleTag).get(0));
				if(getWebElement(sampleTag).size()>=1) {
					logPassed("Able to verify selected tags in "+articleNavigationBtns.get(i).getText()+" detailed author view ");
					status = true;
				}else
					logFailed("Unable to verify selected tags in "+articleNavigationBtns.get(i).getText()+" detailed author view ");
				if(!(i==articleNavigationBtns.size()-1)) {
					status = false;
					click(articleNavigationBtns.get(i+1),"Navigation button");
				}
			}
			if(status)
				logPassed("Able to verify selected tags in detailed author view ");
			else
				logFailed("Unable to verify selected tags in detailed author view ");
		}catch (Exception e) {
			logException(e);
		}
		return status;
	}

	public String selectTagAndGetActiveLinkText(String sampleTag) throws Exception {
		selectValueFromListOfWebElements(popularTags, sampleTag);
		logInfo("Selected tag - "+ sampleTag);
		return getText(activeLinkBtn,"Active Link Text");
	}
	/**
	 * @author kirankumar
	 * @description - to update the active tag
	 * @param sampleTag
	 * @param updatedSampleTag
	 * @return
	 */
	public Boolean verifyUpdateActiveTagFeatureFromTheFilter(String sampleTag,String updatedSampleTag) {
		Boolean status = false;
		try {
			String activeLinkText = selectTagAndGetActiveLinkText(sampleTag);
			String updatedLinkActiveLinkText = selectTagAndGetActiveLinkText(updatedSampleTag);
			if(activeLinkText.equalsIgnoreCase(sampleTag) && updatedLinkActiveLinkText.equalsIgnoreCase(updatedSampleTag)) {
				logPassed("Update active tag feature is verifed , previous selected tag - "+activeLinkText+" & current active tag - "+updatedLinkActiveLinkText);
				status = true;
			}else
				logFailed("Failed to verify Update active tag feature is verifed , previous selected tag - "+activeLinkText+" & current active tag - "+updatedLinkActiveLinkText);			

		}catch (Exception e) {
			logException(e);
		}
		return status;
	}

	/**
	 * @author kirankumar
	 * @description - to select the existing active tag & verify it
	 * @description - 
	 * @param sampleTag
	 * @return
	 */
	public Boolean verifySelectingExistingActiveTagFromTheFilter(String sampleTag) {
		Boolean status = false;
		try {
			String activeLinkText = selectTagAndGetActiveLinkText(sampleTag);
			String updatedLinkActiveLinkText = selectTagAndGetActiveLinkText(sampleTag);
			if(activeLinkText.equalsIgnoreCase(sampleTag) && updatedLinkActiveLinkText.equalsIgnoreCase(sampleTag)) {
				logPassed("Existing active tag is retained upon the selecting the same from filters , previous selected tag - "+activeLinkText+" & current active tag - "+updatedLinkActiveLinkText);
				status = true;
			}else
				logFailed("Failed to retain the existing active tage upon selecting it from the filters , previous selected tag - "+activeLinkText+" & current active tag - "+updatedLinkActiveLinkText);			

		}catch (Exception e) {
			logException(e);
		}
		return status;
	}
	/**
	 * @author kirankumar
	 * @description - to validate popular tags count before & after selecting the count
	 * @return
	 */
	public Boolean verifyPopularTagsCountBeforeAndAfterSelectingAAuthor() {
		Boolean status = false;
		try {
			waitUntilElementFound(authorLinks.get(0));
			int prevCount = popularTags.size();	
			click(authorLinks.get(0),"Author");
			navigateBack();
			waitUntilElementFound(authorLinks.get(0));
			int currentCount = popularTags.size();	
			if(prevCount==currentCount) {
				logPassed("Before selecting the author - "+prevCount+" & after selecting the author  - "+ currentCount);
				status = true;
			} else 
				logFailed("Before selecting the author - "+prevCount+" & after selecting the author  - "+ currentCount);
		}catch (Exception e) {
			logException(e);
		}
		return status;

	}
}
