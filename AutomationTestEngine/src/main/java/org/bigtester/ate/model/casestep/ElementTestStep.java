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
package org.bigtester.ate.model.casestep;

import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.openqa.selenium.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStep defines ....
 * 
 * @author Peidong Hu
 */
public class ElementTestStep extends BaseTestStep implements ITestStep {
	// TOTO add pageObject as another member.

	/**
	 * Instantiates a new test step.
	 * 
	 * @param myWe
	 *            the my we
	 */
	public ElementTestStep(final MyWebElement myWe) {
		super();
		setMyWebElement(myWe);
	}

	/**
	 * {@inheritDoc}
	 */
	@StepLoggable
	public void doStep() throws StepExecutionException{
		try {
			getMyWebElement().doAction();
		} catch (NoSuchElementException e) {
			StepExecutionException pve = new StepExecutionException(
					ExceptionMessage.MSG_WEBELEMENT_NOTFOUND,
					ExceptionErrorCode.WEBELEMENT_NOTFOUND,
					this.getMyWebElement());
			pve.initCause(e);
			throw pve;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return super.getMyWebElement().getMyWd();
	}
}