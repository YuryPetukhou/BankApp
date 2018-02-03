package com.solutions.bankapp.controller.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solutions.bankapp.controller.filter.country.CountryIPResolver;
import com.solutions.bankapp.controller.filter.country.CountryIPResolverImpl;

public class AddressFilter implements Filter {

	private static final String LOCAL_HOST_IP = "0:0:0:0:0:0:0:1";
	private static final String PERMITTED_ISO_CODE = "LV";
	private static final int MAX_REQUESTS_PER_SECOND = 0;

	private final Map<InetAddress, List<LocalDateTime>> frequencyMap;
	private CountryIPResolver countryResolver;

	public AddressFilter() {
		frequencyMap = new HashMap<InetAddress, List<LocalDateTime>>();
		countryResolver = new CountryIPResolverImpl();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		if (ip == null) {
			ip = ((HttpServletRequest) request).getHeader("X-FORWARDED-FOR");
		}
		InetAddress inetAddress = null;
		if (ip.equalsIgnoreCase(LOCAL_HOST_IP)) {
			inetAddress = InetAddress.getLocalHost();
		} else {
			inetAddress = InetAddress.getByName(ip);
		}
		if (countryIsBlocked(ip) || requestFrequencyExceed(inetAddress)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	private boolean requestFrequencyExceed(InetAddress inetAddress) {
		List<LocalDateTime> requestList = frequencyMap.get(inetAddress);
		if (requestList == null) {
			requestList = new ArrayList<LocalDateTime>();
			frequencyMap.put(inetAddress, requestList);
		}
		LocalDateTime now = LocalDateTime.now();
		requestList = requestList.stream().filter(dateTime -> dateTime.isAfter(now.plusSeconds(1)))
				.collect(Collectors.toList());
		requestList.add(now);
		return requestList.size() <= MAX_REQUESTS_PER_SECOND;
	}

	private boolean countryIsBlocked(String ip) throws IOException {
		String countryISOCode = countryResolver.getCountry(ip);
		return !countryISOCode.equals(PERMITTED_ISO_CODE);
	}

}
