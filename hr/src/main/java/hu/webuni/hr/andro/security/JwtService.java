package hu.webuni.hr.andro.security;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.andro.config.HrConfigProperties;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.EmployeeRepository;

@Service
public class JwtService {
	
	@Autowired
	HrConfigProperties hrConfigProperties;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	private static String ISSUER;
	private static Algorithm ALG = Algorithm.HMAC256("asdas");
	private static final String AUTH = "auth";
	private static final String CLAIM_USER = "user";
	private static final String CLAIM_JUNIOR = "junior";
	private static final String CLAIM_SUPERIOR = "superior";
	
	@PostConstruct
	public void securityInit() throws Exception {
		System.out.println(ALG.getClass());
		ISSUER = hrConfigProperties.getIssuer();
		Method m = Class.forName("com.auth0.jwt.algorithms."+hrConfigProperties.getAlgorithm()).getDeclaredMethod(hrConfigProperties.getAlgorithm(), String.class);
		ALG = (Algorithm) m.invoke(ALG, hrConfigProperties.getSecret());
		/*try {
			Method m = ALG.getClass().getDeclaredMethod(hrConfigProperties.getAlgorithm(), String.class);
			ALG = (Algorithm) m.invoke(ALG, hrConfigProperties.getSecret());
		}catch(Exception ex) {
			//System.out.println(ex.getLocalizedMessage());
		}*/
		
	}
	
	public String createToken(EmployeeUserDetails userDetails) {
		JwtDecodedUserData jwtDud = JwtDecodedUserData.createJwtDud(userDetails.getEmployee());
		String token = JWT.create()
			.withSubject(userDetails.getUsername())
			.withClaim(CLAIM_USER, jwtDud.getUserData())
			.withClaim(CLAIM_SUPERIOR, jwtDud.getSuperiorData())
			.withClaim(CLAIM_JUNIOR, jwtDud.getJuniorList())
			.withArrayClaim(AUTH, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withExpiresAt(new Date(System.currentTimeMillis()+hrConfigProperties.getDuration()*3600*1000))
			.withIssuer(ISSUER)
			.sign(ALG);			
		return token;
	}

	public EmployeeUserDetails parseJwt(String token) {
		DecodedJWT decoded = JWT.require(ALG)
				.withIssuer(ISSUER)
				.build()
				.verify(token);
		/*User user = new User(decoded.getSubject(), "...", 
				decoded.getClaim(AUTH).asList(String.class).stream().
					map(SimpleGrantedAuthority::new).collect(Collectors.toList()));*/
		Employee employee = employeeRepository.findByUsername(decoded.getSubject()).orElseThrow(()->new UsernameNotFoundException(decoded.getSubject()));
		EmployeeUserDetails user = null;
		if (employee != null) {
			user = new EmployeeUserDetails(employee);
			JwtDecodedUserData jwtDud = new JwtDecodedUserData();
			Map<String, Object> u = decoded.getClaim(CLAIM_USER).asMap();
			Map<String, String> userData = u.entrySet().stream()
				     .collect(Collectors.toMap(Map.Entry::getKey, e -> (String)e.getValue()));
			jwtDud.setUserData(userData);
			Map<String, Object> s = decoded.getClaim(CLAIM_SUPERIOR).asMap();
			Map<String, String> superiorData = s.entrySet().stream()
				     .collect(Collectors.toMap(Map.Entry::getKey, e -> (String)e.getValue()));
			jwtDud.setSuperiorData(superiorData);
			List<HashMap> j = decoded.getClaim(CLAIM_JUNIOR).asList(HashMap.class);
			List<Map<String,String>> juniorData = new ArrayList<>();
			for (HashMap h : j) {
				Map<String, String> temp = new HashMap<>();
				temp.put("id", (String)h.get("id"));
				temp.put("username", (String)h.get("username"));
				juniorData.add(temp);
			}
			jwtDud.setJuniorList(juniorData);
			user.setJwtDecodedUserData(jwtDud);
		}
		return user;
	}
	
}
