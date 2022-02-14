package hu.webuni.hr.andro.security;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.andro.config.HrConfigProperties;

@Service
public class JwtService {
	
	@Autowired
	HrConfigProperties hrConfigProperties;
	
	private static String ISSUER;
	private static Algorithm ALG = Algorithm.none();
	private static final String AUTH = "auth";
	private static final String CLAIM_USER = "user";
	private static final String CLAIM_EMPLOYEES = "employees";
	private static final String CLAIM_SUPERIOR = "superior";
	
	@PostConstruct
	public void securityInit() {
		ISSUER = hrConfigProperties.getIssuer();
		try {
			Method m = ALG.getClass().getDeclaredMethod(hrConfigProperties.getAlgorithm(), String.class);
			ALG = (Algorithm) m.invoke(ALG, hrConfigProperties.getSecret());
		}catch(Exception ex) {System.out.println(ex.getLocalizedMessage());}
		
	}
	
	public String createToken(EmployeeUserDetails userDetails) {
		Map<String, String> user = new HashMap<>();
		user.put("id", userDetails.getEmployee().getId().toString());
		user.put("name", userDetails.getEmployee().getName());
		user.put("username", userDetails.getEmployee().getUsername());
		String token = JWT.create()
			.withSubject(userDetails.getUsername())
			.withClaim(CLAIM_USER, user)
			.withArrayClaim(AUTH, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withExpiresAt(new Date(System.currentTimeMillis()+hrConfigProperties.getDuration()*3600*1000))
			.withIssuer(ISSUER)
			.sign(ALG);			
		return token;
	}

	public UserDetails parseJwt(String token) {
		DecodedJWT decoded = JWT.require(ALG)
				.withIssuer(ISSUER)
				.build()
				.verify(token);
		User user = new User(decoded.getSubject(), "...", 
				decoded.getClaim(AUTH).asList(String.class).stream().
					map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		System.out.println(decoded.getClaim(CLAIM_USER));
		return user;
	}
	
}
