package org.marketcetera.photon.views;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.marketcetera.core.MSymbol;
import org.marketcetera.photon.marketdata.OptionContractData;

import quickfix.field.PutOrCall;


public class OptionSeriesCollectionTest extends TestCase {
	
	private List<OptionContractData> series;
	private OptionSeriesCollection optionSeriesCollection;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		series = new ArrayList<OptionContractData>();
		series.add(new OptionContractData(new MSymbol("IBM"), new MSymbol("IBM+AE"), 2008, 7, BigDecimal.TEN, PutOrCall.CALL));
		series.add(new OptionContractData(new MSymbol("IBM"), new MSymbol("IBM+RE"), 2008, 7, BigDecimal.TEN, PutOrCall.PUT));
		series.add(new OptionContractData(new MSymbol("IBM"), new MSymbol("IBM+AF"), 2007, 8, BigDecimal.ONE, PutOrCall.CALL));
		series.add(new OptionContractData(new MSymbol("IBM"), new MSymbol("IBM+RF"), 2007, 8, BigDecimal.ONE, PutOrCall.PUT));
		optionSeriesCollection = new OptionSeriesCollection(series);
	}

	public void testGetOptionInfoForSymbol() {
		OptionContractData optionInfo = optionSeriesCollection.getOptionInfoForSymbol(new MSymbol("IBM+AE"));
		assertEquals(PutOrCall.CALL, optionInfo.getPutOrCall());
		assertEquals(BigDecimal.TEN, optionInfo.getStrikePrice());
		assertEquals((Integer)2008, optionInfo.getExpirationYear());

		optionInfo = optionSeriesCollection.getOptionInfoForSymbol(new MSymbol("IBM+RF"));
		assertEquals(PutOrCall.PUT, optionInfo.getPutOrCall());
		assertEquals(BigDecimal.ONE, optionInfo.getStrikePrice());
		assertEquals((Integer)2007, optionInfo.getExpirationYear());

		assertNull(optionSeriesCollection.getOptionInfoForSymbol(new MSymbol("POI")));
	}

	public void testGetOptionContractData() {
		assertSame(series.get(0), optionSeriesCollection.getOptionContractData("IBM", "2008", "JUL", "10", PutOrCall.CALL));
		assertSame(series.get(1), optionSeriesCollection.getOptionContractData("IBM", "2008", "JUL", "10", PutOrCall.PUT));
		assertSame(series.get(2), optionSeriesCollection.getOptionContractData("IBM", "2007", "AUG", "1", PutOrCall.CALL));
		assertSame(series.get(3), optionSeriesCollection.getOptionContractData("IBM", "2007", "AUG", "1", PutOrCall.PUT));
	}

	public void testGetExpirationMonthsForUI() {
		List<String> expectedList = Arrays.asList(new String [] {"JUL", "AUG"});
		assertEquals(expectedList, optionSeriesCollection.getExpirationMonthsForUI());
	}

	public void testGetExpirationYearsForUI() {
		List<String> expectedList = Arrays.asList(new String [] {"2007", "2008"});
		assertEquals(expectedList, optionSeriesCollection.getExpirationYearsForUI());
	}

	public void testGetStrikePricesForUI() {
		List<String> expectedList = Arrays.asList(new String [] {"1", "10"});
		assertEquals(expectedList, optionSeriesCollection.getStrikePricesForUI());
	}

}