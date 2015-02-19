package com.simstock.fetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;

import org.junit.Test;

public class YahooDataFetcherTest {

	private static final String URL = YahooDataFetcher.getBasicURL();
	private static final Calendar fromDate = Calendar.getInstance();
	private static final Calendar toDate = Calendar.getInstance();
	
	YahooDataFetcher yFetcher = new YahooDataFetcher("GOOG");

	@Test
	public void testUrlWithStockNameOnly() {
		assertEquals(URL + "s=GOOG", yFetcher.getURL());
	}

	@Test
	public void testUrlWithFromDate() {
		fromDate.set(2014, 1, 1);
		yFetcher = new YahooDataFetcher("GOOG", fromDate);
		assertEquals(URL + "s=GOOG&a=0&b=1&c=2014", yFetcher.getURL());
	}

	@Test
	public void testUrlWithToDate() {
		fromDate.set(2014, 1, 1);
		toDate.set(2015, 1, 1);
		yFetcher = new YahooDataFetcher("AAPL", fromDate, toDate);
		assertEquals(URL + "s=AAPL&a=0&b=1&c=2014&d=0&e=1&f=2015",
				yFetcher.getURL());
	}

	@Test
	public void testChangeURL() {
		fromDate.set(2013, 3, 21);
		toDate.set(2014, 2, 11);
		yFetcher.setStockSymbol("YHOO");
		yFetcher.setFromDate(fromDate);
		yFetcher.setToDate(toDate);
		assertEquals(URL + "s=YHOO&a=2&b=21&c=2013&d=1&e=11&f=2014",
				yFetcher.getURL());
	}

	@Test
	public void testCsvDownloadedFalse() {
		yFetcher.setStockSymbol("600004.SS");
		File f = yFetcher.csvFile();
		assertFalse(System.currentTimeMillis() - f.lastModified() <= 8000);
	}

	@Test(timeout=8000)
	public void testCsvDownloadedTrue() {
		yFetcher.setStockSymbol("600004.SS");
		File f = yFetcher.csvFile();
		yFetcher.downloadCsv(f);
		assertTrue(f.isFile());
		assertFalse(f.isDirectory());
		assertTrue(System.currentTimeMillis() - f.lastModified() <= 8000);
	}
}
