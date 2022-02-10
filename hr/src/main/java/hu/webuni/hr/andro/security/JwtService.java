package hu.webuni.hr.andro.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
	
	private static final String ISSUER = "HrApp";
	private static final Algorithm ALG = Algorithm.HMAC256("titkos kulcs");
	private static final String AUTH = "auth";
	private static final String CLAIM_USER = "user";
	private static final String CLAIM_EMPLOYEES = "employees";
	private static final String CLAIM_SUPERIOR = "superior";
	
	public String createToken(EmployeeUserDetails userDetails) {
		Map<String, String> user = new HashMap<>();
		user.put("id", userDetails.getEmployee().getId().toString());
		user.put("name", userDetails.getEmployee().getName());
		user.put("username", userDetails.getEmployee().getUsername());
		String token = JWT.create()
			.withSubject(userDetails.getUsername())
			.withClaim(CLAIM_USER, user)
			.withArrayClaim(AUTH, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withExpiresAt(new Date(System.currentTimeMillis()+2*3600*1000))
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
