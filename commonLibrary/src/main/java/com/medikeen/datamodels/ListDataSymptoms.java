package com.medikeen.datamodels;

import java.util.ArrayList;

public class ListDataSymptoms {
	private String title, thumbnailUrl, parametername;
	private int year;
	private double rating;
	public static ArrayList<String> parameters = new ArrayList<String>();
	public boolean isEditEnabled = true;

	/**
	 * @return the parameters
	 */
	public ArrayList<String> getParameters() {
		return parameters;
	}

	public ListDataSymptoms() {
	}

	public ListDataSymptoms(String name, String parameterName) {
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
		parameters.add(parametername);
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

}
