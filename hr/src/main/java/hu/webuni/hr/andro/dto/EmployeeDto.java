package hu.webuni.hr.andro.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
	private Long id;
	private String name;
	private String rank;
	private int payment;
	private LocalDateTime entrance;
	
	public EmployeeDto(Long id, String name, String rank, int payment, LocalDateTime entrance) {
		super();
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.payment = payment;
		this.entrance = entrance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public LocalDateTime getEntrance() {
		return entrance;
	}
	public void setEntrance(LocalDateTime entrance) {
		this.entrance = entrance;
	}
	
	
}
