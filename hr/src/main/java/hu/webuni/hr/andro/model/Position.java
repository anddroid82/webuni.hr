package hu.webuni.hr.andro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;

@NamedEntityGraph(name = "Position.full",includeAllAttributes = true)
@Entity
public class Position {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Education education;
	private Integer minPayment;
	
	public Position() {
		super();
	}
	public Position(Long id, String name, Education education, Integer minPayment) {
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
