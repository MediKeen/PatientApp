package com.medikeen.dataModel;

import java.util.ArrayList;

import android.util.Log;

public class DoctorDTO {

	String DoctorName;
	String DoctorAddress;
	String DoctorImage;
	String DoctorId;
	String DocSpec;
	String DocContact;
	
	public static ArrayList<DoctorDTO> doctorList = new ArrayList<DoctorDTO>();


	public DoctorDTO(){

	}

	public DoctorDTO(String DoctorName,String DoctorAddress,String DocSpec,String DocContact,String image){


		this.DoctorName =DoctorName;
		this.DoctorAddress = DoctorAddress;
		this.DocSpec = DocSpec;
		this.DocContact = DocContact;
		this.DoctorImage = image;
	}

	public void setMatchUserDetails(DoctorDTO matchedUsers){

		Log.d("string", ""+matchedUsers);
		doctorList.add(matchedUsers);
	}

	public static DoctorDTO getMatchedDoctors(int index)
	{   
		if(index < doctorList.size())
		{
			return doctorList.get(index);
		}
		Log.d("size is adpter", ""+doctorList.size());
		return null;
	}

	
	/**
	 * @return the docContact
	 */
	public String getDocContact() {
		return DocContact;
	}
	/**
	 * @param docContact the docContact to set
	 */
	public void setDocContact(String docContact) {
		DocContact = docContact;
	}
	/**
	 * @return the docSpec
	 */
	public String getDocSpec() {
		return DocSpec;
	}
	/**
	 * @param docSpec the docSpec to set
	 */
	public void setDocSpec(String docSpec) {
		DocSpec = docSpec;
	}
	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return DoctorName;
	}
	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		DoctorName = doctorName;
	}
	/**
	 * @return the doctorAddress
	 */
	public String getDoctorAddress() {
		return DoctorAddress;
	}
	/**
	 * @param doctorAddress the doctorAddress to set
	 */
	public void setDoctorAddress(String doctorAddress) {
		DoctorAddress = doctorAddress;
	}
	/**
	 * @return the doctorImage
	 */
	public String getDoctorImage() {
		return DoctorImage;
	}
	/**
	 * @param doctorImage the doctorImage to set
	 */
	public void setDoctorImage(String doctorImage) {
		DoctorImage = doctorImage;
	}
	/**
	 * @return the doctorId
	 */
	public String getDoctorId() {
		return DoctorId;
	}
	/**
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(String doctorId) {
		DoctorId = doctorId;
	}
	
}
