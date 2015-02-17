package com.simstock.fetcher;

import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class YahooDataFetcherTest {

	private static final String URL = YahooDataFetcher.getBasicURL();
	private static final Calendar fromDate = Calendar.getInstance();
	private static final Calendar toDate = Calendar.getInstance();
	YahooDataFetcher yFetcher = new YahooDataFetcher("GOOG");

	@Test
	public void testStockName() {
		assertEquals(URL + "s=GOOG", yFetcher.getURL());
	}

	@Test
	public void testFromDate() {
		fromDate.set(2014, 1, 1);
		yFetcher = new YahooDataFetcher("GOOG", fromDate);
		assertEquals(URL + "s=GOOG&a=0&b=1&c=2014", yFetcher.getURL());
	}

	@Test
	public void testToDate() {
		fromDate.set(2014, 1, 1);
		toDate.set(2015, 1, 1);
		yFetcher = new YahooDataFetcher("AAPL", fromDate, toDate);
		assertEquals(URL + "s=AAPL&a=0&b=1&c=2014&d=0&e=1&f=2015", yFetcher.getURL());
	}

	@Test
	public void testChangeURL() {
		fromDate.set(2013, 3, 21);
		toDate.set(2014, 2, 11);
		yFetcher.setStockSymbol("YHOO");
		yFetcher.setFromDate(fromDate);
		yFetcher.setToDate(toDate);
		assertEquals(URL + "s=YHOO&a=2&b=21&c=2013&d=1&e=11&f=2014", yFetcher.getURL());
	}
}
