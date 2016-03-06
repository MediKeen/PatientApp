package com.medikeen.dataModel;

import java.util.ArrayList;

public class PrescriptionDTO {

	public String tabName,prescriptionTime;
	public static ArrayList<PrescriptionDTO> prescriptionList = new ArrayList<PrescriptionDTO>();


	public PrescriptionDTO(){

	}

	public PrescriptionDTO(String TabName,String tabTime){


		this.tabName =TabName;
		this.prescriptionTime = tabTime;

	}

	public void setMatchUserDetails(PrescriptionDTO matchedUsers){

		prescriptionList.add(matchedUsers);
	}

	public static PrescriptionDTO getMatchedUser(int index)
	{   
		if(index < prescriptionList.size())
		{
			return prescriptionList.get(index);
		}
		return null;
	}
}
