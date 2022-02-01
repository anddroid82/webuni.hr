package hu.webuni.hr.andro.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.validation.DateAfter;

@DateAfter(firstField = "fromDate",secondField = "toDate")
public class VacationDto {
	
	private long id;
	
	private VacationState state;
	
	@Future
	private LocalDate fromDate;
	@Future
	private LocalDate toDate;
	
	@NotNull
	private LocalDateTime submitDateTime;
	
	@NotNull
	private EmployeeDto owner;
	
	private EmployeeDto confirmator;
	
	public VacationDto() {
		super();
	}
	
	//létrehozáshoz
	public VacationDto(LocalDate fromDate, LocalDate toDate, LocalDateTime submitDateTime, EmployeeDto owner) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.submitDateTime = submitDateTime;
		this.owner = owner;
	}

	public VacationDto(long id, VacationState state, LocalDate from, LocalDate to, LocalDateTime submitDateTime, EmployeeDto owner, EmployeeDto confirmator) {
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

	public EmployeeDto getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDto owner) {
		this.owner = owner;
	}

	public EmployeeDto getConfirmator() {
		return confirmator;
	}

	public void setConfirmator(EmployeeDto confirmator) {
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
