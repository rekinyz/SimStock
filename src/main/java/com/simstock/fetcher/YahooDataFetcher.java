package com.simstock.fetcher;

import java.util.Calendar;

/**
 * YahooDataFetcher
 * 
 * Data Fetcher using "Builder" Design Pattern that is introduced in Effective
 * Java
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
		append(this.requestURL, "s=", stockName);
	}

	public YahooDataFetcher(String stockName, Calendar fromDate) {
		this(stockName);
		this.fromDate = fromDate;
		append(this.requestURL, "&b=", fromDate.get(Calendar.DATE));
		append(this.requestURL, "&a=", fromDate.get(Calendar.MONTH) - 1);
		append(this.requestURL, "&c=", fromDate.get(Calendar.YEAR));
	}

	public YahooDataFetcher(String stockName, Calendar fromDate, Calendar toDate) {
		this(stockName, fromDate);
		this.toDate = toDate;
		append(this.requestURL, "&e=", toDate.get(Calendar.DATE));
		append(this.requestURL, "&d=", toDate.get(Calendar.MONTH) - 1);
		append(this.requestURL, "&f=", toDate.get(Calendar.YEAR));
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockSymbol(String stockName) {
		this.stockName = stockName;
		changeURL(stockName);
	}

	public Calendar getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
		changeURL(stockName);
	}

	public Calendar getToDate() {
		return this.toDate;
	}

	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
		changeURL(stockName);
	}

	private void changeURL(String stockName) {
		this.requestURL = new StringBuilder(basicURL);
		append(this.requestURL, "s=", stockName);
		if (getFromDate() != null) {
			append(this.requestURL, "&b=", getFromDate().get(Calendar.DATE));
			append(this.requestURL, "&a=",
					getFromDate().get(Calendar.MONTH) - 1);
			append(this.requestURL, "&c=", getFromDate().get(Calendar.YEAR));
		}
		if (getToDate() != null) {
			append(this.requestURL, "&e=", getToDate().get(Calendar.DATE));
			append(this.requestURL, "&d=", getToDate().get(Calendar.MONTH) - 1);
			append(this.requestURL, "&f=", getToDate().get(Calendar.YEAR));
		}
	}

	private StringBuilder append(StringBuilder sb, String param, Object value) {
		return sb.append(param).append(value);
	}

	public static String getBasicURL() {
		return basicURL;
	}

	public String getURL() {
		return this.requestURL.toString();
	}

}
