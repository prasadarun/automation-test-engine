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
package org.bigtester.ate.model.page;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPageModelBase defines ....
 * 
 * @author Peidong Hu
 */
public class PageModelBase {//NOPMD
	
	/** The i my wd. */
	@XStreamOmitField
	@Autowired
	@Lazy
	protected IMyWebDriver myWd;

	/**
	 * Gets the my wd.
	 *
	 * @return the myWd
	 */
	public  IMyWebDriver getMyWd() {
		return myWd;
	}

	/**
	 * Sets the my wd.
	 *
	 * @param myWd the myWd to set
	 */
	public  void setMyWd(final IMyWebDriver myWd) {
		this.myWd = myWd;
	}
	
	/**
	 * Instantiates a new page model base.
	 *
	 * @param myWd the my wd
	 */
	public PageModelBase(IMyWebDriver myWd) {
		this.myWd = myWd;
	}
	
	/**
	 * Instantiates a new page model base.
	 */
	public PageModelBase() {
		super();
	}
}
