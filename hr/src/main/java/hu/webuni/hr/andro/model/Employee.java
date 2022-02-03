package hu.webuni.hr.andro.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import hu.webuni.hr.andro.validation.BeforeNow;

@NamedEntityGraph(
		name = "Employee.full", 
		attributeNodes = {
				@NamedAttributeNode(value = "position"),
				@NamedAttributeNode(value = "company",subgraph = "Company.full")
		},
		subgraphs = {
				@NamedSubgraph(
						name = "Company.full",
						attributeNodes = {@NamedAttributeNode("companyType")}
				)
		}
)
@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank
	private String name;
	//@NotBlank
	//private String rank;
	
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;
	
	@Min(1)
	private int payment;
	@BeforeNow //a @Past is jó lett volna, csak létre akartam hozni egy sajátot, hogy hogy működik
	private LocalDateTime entrance;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@Column(unique = true)
	private String username;
	private String password;
	
	@ManyToOne
	private Employee superior;
	
	public Employee() {	
	}

	public Employee(Long id, String name, Position position, int payment, LocalDateTime entrance, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.payment = payment;
		this.entrance = entrance;
	}
	
	public Employee(Long id, @NotBlank String name, Position position, @Min(1) int payment, LocalDateTime entrance,
			Company company, String username, String password, Employee superior) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.payment = payment;
		this.entrance = entrance;
		this.company = company;
		this.username = username;
		this.password = password;
		this.superior = superior;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
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
	
	public long getMonthsFromEntrance() {
		LocalDateTime to = LocalDateTime.now();
		long months = this.getEntrance().until(to, ChronoUnit.MONTHS);
		return months;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getSuperior() {
		return superior;
	}

	public void setSuperior(Employee superior) {
		this.superior = superior;
	}

	@Override
	public String toString() {
		return this.id + " " + this.name + " (" + this.position.getName() + ") - " + this.payment + " - " + this.entrance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			return this.getId().equals(((Employee) obj).getId());
		}
		return false;
	}

}

