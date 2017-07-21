package com.aylson.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Spring Bean管理器，可以从beanFactory中根据bean名称获取bean
 * 
 * 
 */ 
public class SpringBeanManager implements BeanFactoryAware {
	private static BeanFactory beanFactory = null;

	public void setBeanFactory(BeanFactory bFactory) throws BeansException {
		if (beanFactory == null) {
			beanFactory = bFactory;
		}
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (beanFactory != null) {
			return beanFactory.getBean(clazz);
		}
		return null;
	}

	public static Object getBean(String beanName) {
		if (beanFactory != null) {
			return beanFactory.getBean(beanName);
		}
		return null;
	}
}
