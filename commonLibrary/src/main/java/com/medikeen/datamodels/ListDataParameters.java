package com.medikeen.datamodels;

import java.util.ArrayList;

public class ListDataParameters {
	private String title, thumbnailUrl, parametername, parameterValue;
	private int year;
	private double rating;
	private ArrayList<String> genre;

	public ListDataParameters() {
	}

	public ListDataParameters(String name, String parameterName) {
		this.title = name;
		this.parametername = parameterName;
		// this.year = year;
		// this.rating = rating;
		// this.genre = genre;
	}

	/**
	 * @return the parametername
	 */
	public String getParametername() {
		return parametername;
	}

	/**
	 * @param parametername
	 *            the parametername to set
	 */
	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	public ArrayList<String> getGenre() {
		return genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
