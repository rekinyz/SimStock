package com.simstock.fetcher;

public enum Param {
	StockSymbol("s="),
	FromDate("a="),
	FromMonth("b="),
	FromYear("c="),
	ToDate("d="),
	ToMonth("e="),
	ToYear("f="),
	Interval("g="),
	
	Daily("d"),
	Weekly("w"),
	Monthly("m");
	
	private String tag;
	
	Param(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
	
	@Override
	public String toString() {
		return tag;
	}
	
}
