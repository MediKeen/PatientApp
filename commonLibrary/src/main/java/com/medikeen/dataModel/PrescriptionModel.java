/**
 * 
 */
package com.medikeen.dataModel;

/**
 * @author Archana
 *
 */
public class PrescriptionModel {
	
	private String DoctorName;
	private String DoctorPhone;
	private String DoctorAdd;
	private String PinCodeAvailiablity;
	private String imagePrescription;
	private String fifteenOff;
	private String thirtyOff;
	
	
	/**
	 * @return the fifteenOff
	 */
	public String getFifteenOff() {
		return fifteenOff;
	}
	/**
	 * @param fifteenOff the fifteenOff to set
	 */
	public void setFifteenOff(String fifteenOff) {
		this.fifteenOff = fifteenOff;
	}
	/**
	 * @return the thirtyOff
	 */
	public String getThirtyOff() {
		return thirtyOff;
	}
	/**
	 * @param thirtyOff the thirtyOff to set
	 */
	public void setThirtyOff(String thirtyOff) {
		this.thirtyOff = thirtyOff;
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
	 * @return the doctorPhone
	 */
	public String getDoctorPhone() {
		return DoctorPhone;
	}
	/**
	 * @param doctorPhone the doctorPhone to set
	 */
	public void setDoctorPhone(String doctorPhone) {
		DoctorPhone = doctorPhone;
	}
	/**
	 * @return the doctorAdd
	 */
	public String getDoctorAdd() {
		return DoctorAdd;
	}
	/**
	 * @param doctorAdd the doctorAdd to set
	 */
	public void setDoctorAdd(String doctorAdd) {
		DoctorAdd = doctorAdd;
	}
	/**
	 * @return the pinCodeAvailiablity
	 */
	public String getPinCodeAvailiablity() {
		return PinCodeAvailiablity;
	}
	/**
	 * @param pinCodeAvailiablity the pinCodeAvailiablity to set
	 */
	public void setPinCodeAvailiablity(String pinCodeAvailiablity) {
		PinCodeAvailiablity = pinCodeAvailiablity;
	}
	/**
	 * @return the imagePrescription
	 */
	public String getImagePrescription() {
		return imagePrescription;
	}
	/**
	 * @param imagePrescription the imagePrescription to set
	 */
	public void setImagePrescription(String imagePrescription) {
		this.imagePrescription = imagePrescription;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PrescriptionModel [DoctorName=" + DoctorName + ", DoctorPhone="
				+ DoctorPhone + ", DoctorAdd=" + DoctorAdd
				+ ", PinCodeAvailiablity=" + PinCodeAvailiablity
				+ ", imagePrescription=" + imagePrescription + "]";
	}
	
}
