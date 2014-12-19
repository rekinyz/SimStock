package com.simstock.fetcher;

public class YahooDataFetcher implements DataFetcher {

	public static final String URL = "http://ichart.yahoo.com/table.csv?";
	
	private String requestURL;
	
	private Param stockSymbol;

	private Param fromDate;
	private Param fromMonth;
	private Param fromYear;
	private Param toDate;
	private Param toMonth;
	private Param toYear;

	private YahooDataFetcher(Builder b) {
		this.requestURL = b.requestURL;
		this.stockSymbol = b.stockSymbol;
		
		this.fromDate = b.fromDate;
		this.fromMonth = b.fromMonth;
		this.fromYear = b.fromYear;
		this.toDate = b.toDate;
		this.toMonth = b.toMonth;
		this.toYear = b.toYear;
	}
	
	public String getRequestURL() {
		return this.requestURL;
	}

	public static class Builder {
		private String requestURL;
		private Param stockSymbol;

		private Param fromDate;
		private Param fromMonth;
		private Param fromYear;

		private Param toDate;
		private Param toMonth;
		private Param toYear;

		public Builder(Param stockName) {
			this.requestURL = requestURL;
			this.stockSymbol = stockName;
		}

		public Builder fromDate(Param fromDate) {
			this.fromDate = fromDate;
			return this;
		}

		public Builder fromMonth(Param fromMonth) {
			this.fromMonth = fromMonth;
			return this;
		}

		public Builder fromYear(Param fromYear) {
			this.fromYear = fromYear;
			return this;
		}

		public Builder toDate(Param toDate) {
			this.toDate = toDate;
			return this;
		}

		public Builder toMonth(Param toMonth) {
			this.toMonth = toMonth;
			return this;
		}

		public Builder toYear(Param toYear) {
			this.toYear = toYear;
			return this;
		}

		public DataFetcher build() {
			return new YahooDataFetcher(this);
		}
	}
}
