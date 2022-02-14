package hu.webuni.hr.andro.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.webuni.hr.andro.model.Employee;

public class JwtDecodedUserData { 
	
	private Map<String, String> userData;
	private Map<String, String> superiorData;
	private List<Map<String, String>> juniorList;
	
	public static JwtDecodedUserData createJwtDud(Employee employee) {
		JwtDecodedUserData jwtDud = new JwtDecodedUserData();
		HashMap<String, String> user = new HashMap<>();
		user.put("id", employee.getId().toString());
		user.put("name", employee.getName());
		user.put("username", employee.getUsername());
		jwtDud.setUserData(user);
		
		HashMap<String, String> superior = new HashMap<>();
		superior.put("id", employee.getSuperior().getId().toString());
		superior.put("username", employee.getSuperior().getUsername());
		jwtDud.setSuperiorData(superior);
		
		List<Map<String, String>> juniors = new ArrayList<>();
		for (Employee j : employee.getJunior()) {
			HashMap<String,String> jData = new HashMap<>();
			jData.put("id", j.getId().toString());
			jData.put("username", j.getUsername());
			juniors.add(jData);
		}
		jwtDud.setJuniorList(juniors);
		return jwtDud;
	}
	
	
	public JwtDecodedUserData() {
		super();
	}

	public JwtDecodedUserData(Map<String, String> userData, Map<String, String> superiorData,
			List<Map<String, String>> juniorList) {
		super();
		this.userData = userData;
		this.superiorData = superiorData;
		this.juniorList = juniorList;
	}

	public Map<String, String> getUserData() {
		return userData;
	}

	public void setUserData(Map<String, String> userData) {
		this.userData = userData;
	}

	public Map<String, String> getSuperiorData() {
		return superiorData;
	}

	public void setSuperiorData(Map<String, String> superiorData) {
		this.superiorData = superiorData;
	}

	public List<Map<String, String>> getJuniorList() {
		return juniorList;
	}

	public void setJuniorList(List<Map<String, String>> juniorList) {
		this.juniorList = juniorList;
	}
	
	
}
