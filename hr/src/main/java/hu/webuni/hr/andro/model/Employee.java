package hu.webuni.hr.andro.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.core.annotation.Order;

import hu.webuni.hr.andro.validation.BeforeNow;

@Entity
@NamedQuery(name = "Employee.findAll",query = "select e from Employee e order by e.name")
public class Employee {
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String rank;
	@Min(1)
	private int payment;
	@BeforeNow //a @Past is jó lett volna, csak létre akartam hozni egy sajátot, hogy hogy működik
	private LocalDateTime entrance;

	public Employee() {
		
	}

	public Employee(Long id, String name, String rank, int payment, LocalDateTime entrance) {
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
	
	public long getMonthsFromEntrance() {
		LocalDateTime to = LocalDateTime.now();
		long months = this.getEntrance().until(to, ChronoUnit.MONTHS);
		return months;
	}

	@Override
	public String toString() {
		return this.id + " " + this.name + " (" + this.rank + ") - " + this.payment + " - " + this.entrance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			return this.getId().equals(((Employee) obj).getId());
		}
		return false;
	}

}
