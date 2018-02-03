package com.solutions.bankapp.controller.filter.country;

import java.net.InetAddress;

public interface CountryIPResolver {
	String getCountry(String ip);
}
