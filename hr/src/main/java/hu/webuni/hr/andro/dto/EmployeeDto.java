package hu.webuni.hr.andro.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hu.webuni.hr.andro.validation.BeforeNow;
import hu.webuni.hr.andro.validation.EmployeeAdd;
import hu.webuni.hr.andro.validation.Unique;

public class EmployeeDto {
	
	@Unique(groups = EmployeeAdd.class)
	private Long id;
	@NotBlank
	private String name;
	private PositionDto position;
	@Min(1)
	private int payment;
	@BeforeNow
	private LocalDateTime entrance;
	
	//@JsonBackReference
	private CompanyDto company;
	
	public EmployeeDto(Long id, String name, PositionDto position, int payment, LocalDateTime entrance, CompanyDto company) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.payment = payment;
		this.entrance = entrance;
		this.company=company;
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
	public PositionDto getPosition() {
		return position;
	}
	public void setPosition(PositionDto position) {
		this.position = position;
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
	
	public CompanyDto getCompany() {
		return company;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	@JsonIgnore
	public long getMonthsFromEntrance() {
		LocalDateTime to=LocalDateTime.now();
		long months = this.getEntrance().until(to, ChronoUnit.MONTHS );
		return months;
	}
	
	@Override
	public String toString() {
		return this.id+" "+this.name+" ("+this.position.getName()+") - "+this.payment+" - "+this.entrance;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof EmployeeDto) {
			return this.getId().equals(((EmployeeDto)obj).getId());
		}
		return false;
	}
	
}
