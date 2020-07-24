package com.wellsfargo.payment.app.model.kafka;

public class AlertMessage {

	private String mail;
	private String phone;
	private String details;
	public AlertMessage() {
		super();
	}
	public AlertMessage(String mail, String phone, String details) {
		super();
		this.mail = mail;
		this.phone = phone;
		this.details = details;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "AlertMessage [mail=" + mail + ", phone=" + phone + ", details=" + details + "]";
	}

}
