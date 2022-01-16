package hu.webuni.hr.andro.model;

public enum Education {
	ERETTSEGI("Érettségi"),
	FOISKOLA("Főiskola"),
	EGYETEM("Egyetem"),
	PHD("Phd");
	private final String title;
	Education(String string) {
		this.title = string;
	}
	public String getTitle() {
		return title;
	}
}