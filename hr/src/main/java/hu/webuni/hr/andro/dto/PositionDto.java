package hu.webuni.hr.andro.dto;

import hu.webuni.hr.andro.model.Education;

public class PositionDto {
	
	private Long id;
	private String name;
	private Education education;
	private Integer minPayment;
	
	public PositionDto() {
		super();
	}
	public PositionDto(Long id, String name, Education education, Integer minPayment) {
		super();
		this.id = id;
		this.name = name;
		this.education = education;
		this.minPayment = minPayment;
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
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}
	public Integer getMinPayment() {
		return minPayment;
	}
	public void setMinPayment(Integer minPayment) {
		this.minPayment = minPayment;
	}
	@Override
	public String toString() {
		return this.name+" ("+this.education.getTitle()+", "+this.minPayment+")";
	}
}

