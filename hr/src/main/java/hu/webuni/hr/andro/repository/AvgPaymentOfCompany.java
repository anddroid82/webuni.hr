package hu.webuni.hr.andro.repository;

public class AvgPaymentOfCompany {
	private Double payment;
	private String rank;
	public AvgPaymentOfCompany(Double payment, String rank) {
		this.payment = payment;
		this.rank = rank;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
}
