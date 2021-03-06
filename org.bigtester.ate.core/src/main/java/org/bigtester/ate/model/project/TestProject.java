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
package org.bigtester.ate.model.project; //NOPMD

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.TestProjectLoggable;
import org.bigtester.ate.model.caserunner.CaseRunnerGenerator;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.reporter.ATEXMLReporter;
import org.bigtester.ate.reporter.AteEmailableReporter;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.testng.TestNG;
import org.testng.reporters.XMLReporterConfig;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.github.javaparser.ParseException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class TestProject defines ....
 *
 * @author Peidong Hu
 */
public class TestProject {

	/** The suite list. */
	@Nullable
	private List<TestSuite> suiteList;

	/** The app ctx. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext appCtx;

	/** The my web driver. */
	@Nullable
	@XStreamOmitField
	private Optional<IMyWebDriver> myWebDriver = Optional.empty();


	/** The global init xmlfiles. */
	private Resource globalInitXmlFile;

	/** The step think time. */
	private int stepThinkTime;

	/** The mailer. */
	private Mailer mailer;

	/** The test project listener. */
	@Nullable
	@XStreamOmitField
	private TestProjectListener testProjectListener;

	/** The testng. */
	@XStreamOmitField
	final private TestNG testng = new TestNG();

	/**
	 * Instantiates a new test project.
	 *
	 * @param globalInitXmlFile
	 *            the global init xml file
	 * @param testProjectListener
	 *            the test project listener
	 */
	public TestProject(Resource globalInitXmlFile) {
		this.globalInitXmlFile = globalInitXmlFile;
	}

	/**
	 * Gets the suite list.
	 *
	 * @return the suiteList
	 */
	public List<TestSuite> getSuiteList() {
		final List<TestSuite> retVal = suiteList;
		if (null == retVal) {
			throw new IllegalStateException(
					"suiteList is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * Sets the suite list.
	 *
	 * @param suiteList
	 *            the suiteList to set
	 */
	public void setSuiteList(List<TestSuite> suiteList) {
		this.suiteList = suiteList;
	}

	/**
	 * Run suites.
	 *
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	@TestProjectLoggable (level=ATELogLevel.INFO)
	public void runSuites() throws ClassNotFoundException, ParseException,
			IOException {

		final TestProjectListener tla = new TestProjectListener(this);
		final TestCaseResultModifier repeatStepResultModifier = new TestCaseResultModifier();

		testng.addListener(tla);
		testng.addListener(repeatStepResultModifier);

		ATEXMLReporter rng = new ATEXMLReporter();
		rng.setStackTraceOutputMethod(XMLReporterConfig.STACKTRACE_NONE);
		AteEmailableReporter emailReport = new AteEmailableReporter();
		testng.addListener(rng);
		testng.addListener(emailReport);
		CaseRunnerGenerator crg = new CaseRunnerGenerator(this.getSuiteList());
		crg.createCaseRunners();
		if (0 == crg.loadCaseRunnerClasses()) {
			throw new ParseException("case runner generator error");
		}
		final List<XmlPackage> packages = new ArrayList<XmlPackage>();

		for (TestSuite tempSuite : getSuiteList()) {

			XmlPackage xmlpackage = new XmlPackage();
			xmlpackage.setName(crg.getBasePackageName() + "." + tempSuite.getSuiteName());

			packages.add(xmlpackage);

		}
		List<XmlSuite> xmlSuites = new ArrayList<XmlSuite>();
		XmlSuite xmlProject = new XmlSuite();

		XmlTest test = new XmlTest(xmlProject);
		test.setPackages(packages);
		xmlSuites.add(xmlProject);
		if (xmlSuites.isEmpty()) {
			throw new IllegalStateException("xmlsuites are not populated.");
		} else {
			testng.setXmlSuites(xmlSuites);

			testng.run();
			
			LogbackWriter.writeAppInfo("To enable system log, please configure the logback-production.xml");

		}

	}

	/**
	 * @return the stepThinkTime
	 */
	public int getStepThinkTime() {
		return stepThinkTime;
	}

	/**
	 * @param stepThinkTime
	 *            the stepThinkTime to set
	 */
	public void setStepThinkTime(int stepThinkTime) {
		this.stepThinkTime = stepThinkTime;
	}

	/**
	 * @return the testProjectListener
	 */
	public TestProjectListener getTestProjectListener() {

		final TestProjectListener testProjectListener2 = testProjectListener;
		if (testProjectListener2 == null) {
			throw GlobalUtils
					.createNotInitializedException("testProjectListener");
		} else {
			return testProjectListener2;
		}
	}

	/**
	 * @param testProjectListener
	 *            the testProjectListener to set
	 */
	public void setTestProjectListener(TestProjectListener testProjectListener) {
		this.testProjectListener = testProjectListener;
	}

	/**
	 * @return the appCtx
	 */
	public ApplicationContext getAppCtx() {
		final ApplicationContext retVal = appCtx;
		if (null == retVal) {
			throw new IllegalStateException(
					"application context is not correctly initialized.");

		} else {
			return retVal;
		}
	}

	/**
	 * @param appCtx
	 *            the appCtx to set
	 */
	public void setAppCtx(ApplicationContext appCtx) {
		this.appCtx = appCtx;
	}

	/**
	 * @return the globalInitXmlfile
	 */
	public Resource getGlobalInitXmlFile() {
		return globalInitXmlFile;
	}

	/**
	 * @param globalInitXmlfile
	 *            the globalInitXmlfile to set
	 * @throws IOException
	 */
	public void setGlobalInitXmlFile(Resource globalInitXmlFile)
			throws IOException {
		this.globalInitXmlFile = globalInitXmlFile;
	}

	/**
	 * @return the testng
	 */
	public TestNG getTestng() {
		return testng;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		String retVal = "Test Project with following suites,";//NOPMD
		final List<TestSuite> suiteList2 = this.suiteList;
		if (suiteList2 != null) {
			for (TestSuite suite: suiteList2) {
				retVal = retVal + "\r\n" +  suite.toString() ;//NOPMD
			}
		}
		return retVal;
	}

	/**
	 * @return the myWebDriver
	 */
	public Optional<IMyWebDriver> getMyWebDriver() {
		return myWebDriver;
	}

	/**
	 * @param myWebDriver the myWebDriver to set
	 */
	public void setMyWebDriver(Optional<IMyWebDriver> myWebDriver) {
		this.myWebDriver = myWebDriver;
	}

	/**
	 * @return the mailer
	 */
	public Mailer getMailer() {
		return mailer;
	}

	/**
	 * @param mailer the mailer to set
	 */
	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}

}
