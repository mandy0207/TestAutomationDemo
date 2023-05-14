package factory;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import context.WebDriverContext;
import pages.BasePage;

public class PageInstancesFactory {

	/**
	 * Get an instance of a page object class
	 * 
	 * @param <T>  The Page Object class type
	 * @param type
	 * @return an instance of the page class
	 */
	public static <T extends BasePage> T getInstance(Class<T> type) {
		try {
			return type.getConstructor(WebDriver.class).newInstance(WebDriverContext.getDriver());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
