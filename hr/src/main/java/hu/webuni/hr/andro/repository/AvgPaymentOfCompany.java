package hu.webuni.hr.andro.repository;

import hu.webuni.hr.andro.model.Position;

public class AvgPaymentOfCompany {
	private Double payment;
	private Position position;
	public AvgPaymentOfCompany(Double payment, Position position) {
		this.payment = payment;
		this.position = position;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
}
