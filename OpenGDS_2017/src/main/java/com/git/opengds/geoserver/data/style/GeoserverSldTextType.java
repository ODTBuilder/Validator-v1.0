package com.git.opengds.geoserver.data.style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoserverSldTextType {
	private List<String> smallTextList;
	private List<String> mediumTextList;
	private List<String> largeTextList;
	private List<String> exceptTextList;

	public GeoserverSldTextType() {
		String[] smallTextArray = new String[] { "A0013440", "A0013441", "A0013442", "A0013443", "A0013444", "A0013445",
				"A0013446", "F0017130", "F0017131", "F0027132", "H0027133", "H0027134", "H0049110", "H0049111",
				"H0049112", "H0049113", "H0049114", "H0049115", "H0049116", "H0049120", "H0049121", "H0049122",
				"H0049123", "H0049124", "H0049125", "H0049130", "H0049131", "H0049132", "H0049133", "H0049134",
				"H0049140", "H0049141", "H0049142", "H0049143", "H0049144", "H0049145", "H0049146", "H0049147",
				"H0049150", "H0049151", "H0049152", "H0049154", "H0049160", "H0049161", "H0049162" };

		String[] mediumTextArray = new String[] { "H0049213", "H0049214", "H0049215", "H0049216", "H0049217", "H0049220",
				"H0049221", "H0049222", "H0049223", "H0049224", "H0049225", "H0049226", "H0049230", "H0049231",
				"H0049232", "H0049233"};
		
		String[] largeTextArray = new String[] { "H0049210", "H0049211", "H0049212" };
		
		String[] exceptTextArray = new String[] {"H0059153", "H0040000"};
		
		smallTextList = new ArrayList<String>(Arrays.asList(smallTextArray));
		mediumTextList = new ArrayList<String>(Arrays.asList(mediumTextArray));
		largeTextList = new ArrayList<String>(Arrays.asList(largeTextArray));
		exceptTextList = new ArrayList<String>(Arrays.asList(exceptTextArray));
	}

	public List<String> getSmallTextList() {
		return smallTextList;
	}


	public List<String> getMediumTextList() {
		return mediumTextList;
	}


	public List<String> getLargeTextList() {
		return largeTextList;
	}
	public List<String> getExceptTextList() {
		return exceptTextList;
	}
	
}
