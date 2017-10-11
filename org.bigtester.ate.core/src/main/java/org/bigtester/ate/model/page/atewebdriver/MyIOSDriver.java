/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.page.atewebdriver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyIOSDriver defines ....
 * 
 * @author Wei Shao
 */
public class MyIOSDriver extends AbstractWebDriverBase implements IMyWebDriver {
			
	/** The Constant BROWSERNAME. */
	/*private static final String BROWSERNAME = "IOS";*/
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.IOS.driver";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "osx/IOS/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "osx/IOS/64bit/";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSER0SXFILENAME = "IOSdriver";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public WebDriver getWebDriver() {
		return super.getWebDriver();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = getWebDriver();
		if (null == retVal) {
			OSinfo osinfo = new OSinfo();
			EPlatform platform = osinfo.getOSname();
			String driverPath = GlobalUtils.getDriverPath(); //NOPMD
			
			switch (platform) {
				case Windows_32:
					if (driverPath == null)
						System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
								           + BROWSEROSX32PATH + BROWSER0SXFILENAME);
					else
						System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
								           + BROWSEROSX32PATH + BROWSER0SXFILENAME);
					break;
				case Windows_64:
					if (driverPath == null)
						System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
								           + BROWSEROSX64PATH + BROWSER0SXFILENAME);
					else
						System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
								           + BROWSEROSX64PATH + BROWSER0SXFILENAME);
					break;
				default:
					throw GlobalUtils.createNotInitializedException("operating system is not supported ");
			}			
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("deviceName", "iPhone 8");
			capabilities.setCapability("platformVersion", "11.0");
			capabilities.setCapability("app", "https://s3.amazonaws.com/appium/TestApp8.4.app.zip");
			capabilities.setCapability("browserName", "");
			capabilities.setCapability("deviceOrientation", "portrait");
			capabilities.setCapability("appiumVersion", "1.6.5");

			retVal = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
			
			setWebDriver(retVal);
		}		
		return retVal;
	}

}