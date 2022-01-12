package hu.webuni.hr.andro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.config.SmartConfigurationProperties;
import hu.webuni.hr.andro.model.Employee;

@Service
public class SmartEmployeeService extends EmployeeAbstractService implements EmployeeService {
	
	@Autowired
	SmartConfigurationProperties smartConfProp;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		long months = employee.getMonthsFromEntrance();
		byte monthNumInYears=12;
		int percent=0;
		
		float[] sections=smartConfProp.getSections();
		int[] percents=smartConfProp.getPercents();
		
		for (int i=0;i<sections.length;i++) {
			if (months >= sections[i]*monthNumInYears) {
				percent=percents[i];
				break;
			}
		}
		
		return percent;
	}

}
