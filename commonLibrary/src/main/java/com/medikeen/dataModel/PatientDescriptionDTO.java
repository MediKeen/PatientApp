package com.medikeen.dataModel;

import java.util.ArrayList;

public class PatientDescriptionDTO {

	public String patientName,patientAddress,imageURL;
	public static ArrayList<PatientDescriptionDTO> prescriptionList = new ArrayList<PatientDescriptionDTO>();


	public PatientDescriptionDTO(){

	}

	public PatientDescriptionDTO(String PatientName,String PatientAddress,String ImageUrl){


		this.patientName =PatientName;
		this.patientAddress = PatientAddress;
		this.imageURL = ImageUrl;

	}

	public void setMatchUserDetails(PatientDescriptionDTO matchedUsers){

		prescriptionList.add(matchedUsers);
	}

	public static PatientDescriptionDTO getMatchedUser(int index)
	{   
		if(index < prescriptionList.size())
		{
			return prescriptionList.get(index);
		}
		return null;
	}
}
