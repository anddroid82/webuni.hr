package hu.webuni.hr.andro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "smart")
public class SmartConfigurationProperties {
	
	float[] sections;
	int[] percents;
	public float[] getSections() {
		return sections;
	}
	public void setSections(float[] sections) {
		this.sections = sections;
	}
	public int[] getPercents() {
		return percents;
	}
	public void setPercents(int[] percents) {
		this.percents = percents;
	}
	
	
	
}
