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
		assertEquals(URL + "s=GOOG&b=1&a=0&c=2014", yFetcher.getURL());
	}

	@Test
	public void testToDate() {
		fromDate.set(2014, 1, 1);
		toDate.set(2015, 1, 1);
		yFetcher = new YahooDataFetcher("AAPL", fromDate, toDate);
		assertEquals(URL + "s=AAPL&b=1&a=0&c=2014&e=1&d=0&f=2015", yFetcher.getURL());
	}

	@Test
	public void testChangeURL() {
		fromDate.set(2013, 3, 21);
		toDate.set(2014, 2, 11);
		yFetcher.setStockSymbol("YHOO");
		yFetcher.setFromDate(fromDate);
		yFetcher.setToDate(toDate);
		assertEquals(URL + "s=YHOO&b=21&a=2&c=2013&e=11&d=1&f=2014", yFetcher.getURL());
	}
}
