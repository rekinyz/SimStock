package com.simstock.services;

import java.util.Calendar;

import com.simstock.data.Stock;

public class StockDaoService extends DataAccessService<Stock> {
	
	public Stock findStockByDate(Calendar date){
		return find(date);
	}
}
