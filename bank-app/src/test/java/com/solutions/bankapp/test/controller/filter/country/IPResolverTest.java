package com.solutions.bankapp.test.controller.filter.country;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.maxmind.geoip2.record.Country;
import com.solutions.bankapp.controller.filter.country.CountryIPResolver;
import com.solutions.bankapp.controller.filter.country.CountryIPResolverImpl;

public class IPResolverTest {

	private CountryIPResolver resolver;
	
	@Before
	public void init() {
		resolver = new CountryIPResolverImpl();
	}
	
	@Test
	public void negativeTest() {
		String ip = "80.232.255.186";
		assertFalse(resolver.getCountry(ip).equals("BY"));
	}

	@Test
	public void positiveTest() {
		String ip = "80.232.255.186";
		assertTrue(resolver.getCountry(ip).equals("LV"));
	}

}
