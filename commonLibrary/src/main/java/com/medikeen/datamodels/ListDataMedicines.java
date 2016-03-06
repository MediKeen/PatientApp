package com.medikeen.datamodels;

import java.util.ArrayList;

public class ListDataMedicines {
	private String title, thumbnailUrl, medicineName, medicineDosage;
	private boolean isMorning, isAfternoon, isEvening, isNight;
	public static ArrayList<String> medicineList = new ArrayList<String>();

	/**
	 * @return the medicineList
	 */
	public static ArrayList<String> getMedicineList() {
		return medicineList;
	}

	private boolean isGroupHeader = false;

	/**
	 * @return the isGroupHeader
	 */
	public boolean isGroupHeader() {
		return isGroupHeader;
	}

	/**
	 * @param isGroupHeader
	 *            the isGroupHeader to set
	 */
	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}

	public ListDataMedicines(String title) {
		this(-1, title, null);
		isGroupHeader = true;
		this.title = title;
	}

	public ListDataMedicines(int icon, String medicineName,
			String medicineDosage) {
		super();
		this.medicineName = medicineName;
		this.medicineDosage = medicineDosage;
	}

	/**
	 * @return the medicineName
	 */
	public String getMedicineName() {
		return medicineName;
	}

	/**
	 * @param medicineName
	 *            the medicineName to set
	 */
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
		medicineList.add(medicineName);
	}

	/**
	 * @return the medicineDosage
	 */
	public String getMedicineDosage() {
		return medicineDosage;
	}

	/**
	 * @param medicineDosage
	 *            the medicineDosage to set
	 */
	public void setMedicineDosage(String medicineDosage) {
		this.medicineDosage = medicineDosage;
	}

	public ListDataMedicines() {
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

	public boolean isEvening() {
		return isEvening;
	}

	public void setEvening(boolean isEvening) {
		this.isEvening = isEvening;
	}

	public boolean isMorning() {
		return isMorning;
	}

	public void setMorning(boolean isMorning) {
		this.isMorning = isMorning;
	}

	public boolean isAfternoon() {
		return isAfternoon;
	}

	public void setAfternoon(boolean isAfternoon) {
		this.isAfternoon = isAfternoon;
	}

	public boolean isNight() {
		return isNight;
	}

	public void setNight(boolean isNight) {
		this.isNight = isNight;
	}

}
