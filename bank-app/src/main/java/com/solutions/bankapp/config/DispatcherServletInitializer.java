package com.solutions.bankapp.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.solutions.bankapp.controller.filter.AddressFilter;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebApplicationContextConfig.class, HibernateConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		 return new String[] { "/" };
	}

//	@Override
//    protected Filter[] getServletFilters() {
//        Filter countryFilter=new AddressFilter();
//        return new Filter[] {countryFilter};
//    }
}
