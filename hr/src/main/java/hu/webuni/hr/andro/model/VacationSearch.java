package hu.webuni.hr.andro.model;

import java.time.LocalDate;

public class VacationSearch {

	private VacationState state;
	private String ownerName;
	private String confirmatorName;
	private LocalDate submitFrom;
	private LocalDate submitTo;
	private LocalDate fromDate;
	private LocalDate toDate;
	
	public VacationSearch() {
		super();
	}

	public VacationSearch(VacationState state, String ownerName, String confirmatorName, LocalDate submitFrom,
			LocalDate submitTo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.state = state;
		this.ownerName = ownerName;
		this.confirmatorName = confirmatorName;
		this.submitFrom = submitFrom;
		this.submitTo = submitTo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public VacationState getState() {
		return state;
	}

	public void setState(VacationState state) {
		this.state = state;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getConfirmatorName() {
		return confirmatorName;
	}

	public void setConfirmatorName(String confirmatorName) {
		this.confirmatorName = confirmatorName;
	}

	public LocalDate getSubmitFrom() {
		return submitFrom;
	}

	public void setSubmitFrom(LocalDate submitFrom) {
		this.submitFrom = submitFrom;
	}

	public LocalDate getSubmitTo() {
		return submitTo;
	}

	public void setSubmitTo(LocalDate submitTo) {
		this.submitTo = submitTo;
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

	@Override
	public String toString() {
		return "VacationSearch [state=" + state + ", ownerName=" + ownerName + ", confirmatorName=" + confirmatorName
				+ ", submitFrom=" + submitFrom + ", submitTo=" + submitTo + ", fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}
	
	
	
	
}
