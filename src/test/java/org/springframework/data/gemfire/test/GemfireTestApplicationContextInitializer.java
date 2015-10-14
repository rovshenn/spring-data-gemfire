/*
 * Copyright 2002-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.data.gemfire.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @author David Turanski
 * @author John Blum
 */
public class GemfireTestApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Log LOG = LogFactory.getLog(GemfireTestApplicationContextInitializer.class);

	public static final String GEMFIRE_TEST_RUNNER_DISABLED = "org.springframework.data.gemfire.test.GemfireTestRunner.nomock";

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String gemfireTestRunnerDisabled = System.getProperty(GEMFIRE_TEST_RUNNER_DISABLED, Boolean.FALSE.toString());

		if (isGemFireTestRunnerDisable(gemfireTestRunnerDisabled)) {
			LOG.warn(String.format("WARNING - Mocks disabled; Using real GemFire components (%1$s = %2$s)",
				GEMFIRE_TEST_RUNNER_DISABLED, gemfireTestRunnerDisabled));
		}
		else {
			applicationContext.getBeanFactory().addBeanPostProcessor(new GemfireTestBeanPostProcessor());
		}
	}

	private boolean isGemFireTestRunnerDisable(final String systemPropertyValue) {
		return (Boolean.valueOf(StringUtils.trimAllWhitespace(systemPropertyValue))
			|| "yes".equalsIgnoreCase(systemPropertyValue));
	}

}
