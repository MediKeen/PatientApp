package com.medikeen.datamodels;

import java.util.ArrayList;

public class ListDataTests {
	private String title, testName;
	private int year;
	private double rating;
	public static ArrayList<String> tests = new ArrayList<String>();

	public ListDataTests() {
	}

	public ListDataTests(String name) {
		this.title = name;
	}

	/**
	 * @return the tests
	 */
	public ArrayList<String> getTests() {
		return tests;
	}

	/**
	 * @return the testName
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName
	 *            the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
		tests.add(testName);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
