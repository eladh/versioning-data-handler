package org.product.webserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SpringContext {

	private static ApplicationContext applicationContext;

	@Autowired
	public SpringContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static <T> T getBean(Class<T> var1) {
		return applicationContext.getBean(var1);
	}
}