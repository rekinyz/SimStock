package com.simstock.fetcher;

import java.util.Calendar;

/**
 * YahooDataFetcher
 * 
 * Simplify the Yahoo Stock API, build the data fetching URL with stock name,
 * from date and to date only
 * 
 * @author rekinyz
 */
public class YahooDataFetcher implements DataFetcher {

	private static final String basicURL = "http://ichart.yahoo.com/table.csv?";
	private StringBuilder requestURL = new StringBuilder(basicURL);

	private String stockName;
	private Calendar fromDate;
	private Calendar toDate;

	public YahooDataFetcher(String stockName) {
		this.stockName = stockName;
		append(requestURL, "s=", stockName);
	}

	public YahooDataFetcher(String stockName, Calendar fromDate) {
		this(stockName);
		this.fromDate = fromDate;
		append(requestURL, "&b=", fromDate.get(Calendar.DATE));
		append(requestURL, "&a=", fromDate.get(Calendar.MONTH) - 1);
		append(requestURL, "&c=", fromDate.get(Calendar.YEAR));
	}

	public YahooDataFetcher(String stockName, Calendar fromDate, Calendar toDate) {
		this(stockName, fromDate);
		this.toDate = toDate;
		append(requestURL, "&e=", toDate.get(Calendar.DATE));
		append(requestURL, "&d=", toDate.get(Calendar.MONTH) - 1);
		append(requestURL, "&f=", toDate.get(Calendar.YEAR));
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockSymbol(String stockName) {
		this.stockName = stockName;
		changeURL(stockName);
	}

	public Calendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
		changeURL(stockName);
	}

	public Calendar getToDate() {
		return toDate;
	}

	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
		changeURL(stockName);
	}

	private void changeURL(String stockName) {
		requestURL = new StringBuilder(basicURL);
		append(requestURL, "s=", stockName);
		if (getFromDate() != null) {
			append(requestURL, "&b=", getFromDate().get(Calendar.DATE));
			append(requestURL, "&a=", getFromDate().get(Calendar.MONTH) - 1);
			append(requestURL, "&c=", getFromDate().get(Calendar.YEAR));
		}
		if (getToDate() != null) {
			append(requestURL, "&e=", getToDate().get(Calendar.DATE));
			append(requestURL, "&d=", getToDate().get(Calendar.MONTH) - 1);
			append(requestURL, "&f=", getToDate().get(Calendar.YEAR));
		}
	}

	private StringBuilder append(StringBuilder sb, String param, Object value) {
		return sb.append(param).append(value);
	}

	public static String getBasicURL() {
		return basicURL;
	}

	public String getURL() {
		return requestURL.toString();
	}

}
