package com.solutions.bankapp.controller.filter.country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryIPResolverImpl implements CountryIPResolver {

	private static final String USER_AGENT = "Mozilla/5.0";

	@Override
	public String getCountry(String ip) {
		String url = "http://freegeoip.net/json/" + ip;
		String country = null;
		try {
			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			if (con.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			ObjectMapper mapper = new ObjectMapper();
			CountryResponse countryResponse = mapper.readValue(response.toString(), CountryResponse.class);
			country = countryResponse.getCountry();
			if (country == null) {
				throw new RuntimeException("Failed: External services unavailable");
			}
			in.close();

		} catch (MalformedURLException e) {
			throw new RuntimeException("Failed : MalformedURLException");
		} catch (IOException e) {
			throw new RuntimeException("Failed : IOException");
		}

		return country;
	}

}
