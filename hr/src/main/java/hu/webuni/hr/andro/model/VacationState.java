package hu.webuni.hr.andro.model;

public enum VacationState {
	NEW("Beadott"), AGREE("Jóváhagyva"), DENIED("Elutasítva");

	private final String title;

	VacationState(String string) {
		this.title = string;
	}

	public String getTitle() {
		return title;
	}
}
