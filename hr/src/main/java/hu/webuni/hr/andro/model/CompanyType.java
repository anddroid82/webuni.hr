package hu.webuni.hr.andro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="companytype")
public class CompanyType {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public CompanyType() {
		super();
	}

	public CompanyType(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	@Override
	public String toString() {
		return this.id+" "+this.name;
	}
}
