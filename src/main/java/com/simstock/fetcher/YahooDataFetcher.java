package com.simstock.fetcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.logging.Logger;

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
	Logger log = Logger.getLogger(YahooDataFetcher.class.getName());

	private String stockName;
	private Calendar fromDate;
	private Calendar toDate;

	public YahooDataFetcher(String stockName) {
		this.stockName = stockName;
		appendStockName();
	}

	public YahooDataFetcher(String stockName, Calendar fromDate) {
		this(stockName);
		this.fromDate = fromDate;
		appendFromDate();
	}

	public YahooDataFetcher(String stockName, Calendar fromDate, Calendar toDate) {
		this(stockName, fromDate);
		this.toDate = toDate;
		appendToDate();
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockSymbol(String stockName) {
		this.stockName = stockName;
		changeURL();
	}

	public Calendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
		changeURL();
	}

	public Calendar getToDate() {
		return toDate;
	}

	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
		changeURL();
	}

	public static String getBasicURL() {
		return basicURL;
	}

	public String getURL() {
		log.info(requestURL.toString());
		return requestURL.toString();
	}

	private void changeURL() {
		requestURL = new StringBuilder(basicURL);
		appendStockName();
		appendFromDate();
		appendToDate();
	}

	private void appendStockName() {
		append("s=", getStockName());
	}

	private void appendToDate() {
		if (getToDate() != null) {
			append("&d=", getToDate().get(Calendar.MONTH) - 1);
			append("&e=", getToDate().get(Calendar.DATE));
			append("&f=", getToDate().get(Calendar.YEAR));
		}
	}

	private void appendFromDate() {
		if (getFromDate() != null) {
			append("&a=", getFromDate().get(Calendar.MONTH) - 1);
			append("&b=", getFromDate().get(Calendar.DATE));
			append("&c=", getFromDate().get(Calendar.YEAR));
		}
	}

	private StringBuilder append(String param, Object value) {
		return requestURL.append(param).append(value);
	}

	protected File csvFile() {
		String path = "src/main/resources/";
		String file = getStockName().replace('.', '-') + ".csv";
		return new File(path + file);
	}

	protected void downloadCsv(File target) {
		URL website;
		try {
			website = new URL(getURL());
			Files.copy(website.openStream(), target.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (MalformedURLException e) {
			log.info("can not download file");
		} catch (IOException e) {
			log.info("file not found");
		}
	}

	protected void downloadCsv() {
		downloadCsv(csvFile());
	}

}
