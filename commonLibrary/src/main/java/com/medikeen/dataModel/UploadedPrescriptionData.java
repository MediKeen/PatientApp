package com.medikeen.dataModel;

public class UploadedPrescriptionData {

	private String DeliveryAddress;
	private String orderId;
	private String orderAmount;
	private String placedDate;
	private String orderStatus;
	private String orderPlaceDate;



	/**
	 * @return the orderPlaceDate
	 */
	public String getOrderPlaceDate() {
		return orderPlaceDate;
	}
	/**
	 * @param orderPlaceDate the orderPlaceDate to set
	 */
	public void setOrderPlaceDate(String orderPlaceDate) {
		this.orderPlaceDate = orderPlaceDate;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderAmount
	 */
	public String getOrderAmount() {
		return orderAmount;
	}
	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UploadedPrescriptionData [DeliveryAddress=" + DeliveryAddress
				+ ", orderId=" + orderId + ", orderAmount=" + orderAmount
				+ ", placedDate=" + placedDate + ", orderStatus=" + orderStatus
				+ "]";
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return DeliveryAddress;
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		DeliveryAddress = deliveryAddress;
	}


}
