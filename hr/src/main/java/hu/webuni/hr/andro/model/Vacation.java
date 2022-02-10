package hu.webuni.hr.andro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;


@NamedEntityGraph(
		name = "Vacation.full", 
		attributeNodes = {
				@NamedAttributeNode(value = "owner", subgraph = "Employee.full"),
				@NamedAttributeNode(value = "confirmator",subgraph = "Employee.full")
		},
		subgraphs = {
				@NamedSubgraph(
						name = "Employee.full",
						attributeNodes = {
								@NamedAttributeNode("position"),
								@NamedAttributeNode(value="company"),
								@NamedAttributeNode(value="superior", subgraph = "Superior.full"),
								@NamedAttributeNode("junior"),
								@NamedAttributeNode("roles")
						}
				),
				@NamedSubgraph(
						name = "Superior.full",
						attributeNodes = {
								@NamedAttributeNode("position")
						}
				)
				
		}
)
@Entity
public class Vacation {

	@Id
	@GeneratedValue
	private long id;
	
	private VacationState state;
	
	private LocalDate fromDate;
	private LocalDate toDate;
	
	private LocalDateTime submitDateTime;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Employee owner;
	
	@ManyToOne
	@JoinColumn(name = "confirmator_id")
	private Employee confirmator;
	
	public Vacation() {
		super();
	}

	public Vacation(long id, VacationState state, LocalDate from, LocalDate to, LocalDateTime submitDateTime, Employee owner, Employee confirmator) {
		super();
		this.id = id;
		this.state = state;
		this.fromDate = from;
		this.submitDateTime=submitDateTime;
		this.toDate = to;
		this.owner = owner;
		this.confirmator = confirmator;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VacationState getState() {
		return state;
	}

	public void setState(VacationState state) {
		this.state = state;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public Employee getConfirmator() {
		return confirmator;
	}

	public void setConfirmator(Employee confirmator) {
		this.confirmator = confirmator;
	}

	@Override
	public String toString() {
		return "Vacation [id=" + id + ", state=" + state + ", from=" + fromDate + ", to=" + toDate + ", owner=" + owner
				+ ", confirmator=" + confirmator + "]";
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDateTime getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(LocalDateTime submitDateTime) {
		this.submitDateTime = submitDateTime;
	}
	
	
	
}
