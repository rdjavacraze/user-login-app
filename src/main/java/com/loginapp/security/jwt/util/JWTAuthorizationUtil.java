package com.loginapp.security.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTAuthorizationUtil {

	   private static final String secret = "mySecret";
	public String generateToken(String username){
		Map<String,Object> claims=new HashMap<>();
		return generateToken(username, claims);
	}
	
	
	public String generateToken(String username,Map<String,Object> claims){
		return  Jwts.builder().setClaims(claims).setIssuedAt(new Date())
				.setSubject(username).setExpiration(new Date(System.currentTimeMillis()+(300000L
				)))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		
		
		
	}
	
	//Extracting claims
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	private Claims extractAllClaims(String token){
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	
	//--------------end-----------------
	
	
	
	
	
	public String getUserNameFromToken(String token) {

		return extractClaim(token, Claims::getSubject);
	}
	
	public Date getExpirationDate(String token){
		return extractClaim(token,Claims::getExpiration);
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return userDetails.getUsername().equalsIgnoreCase(username) && isTokenExpired(token);

	}
	private boolean isTokenExpired(String token){
		Date exp=getExpirationDate(token);
			System.out.println("-Expiration-->"+exp);
			Date current=new Date();
		  return current.before(exp);
	}
	
	private String getTokenFromCookie(HttpServletRequest request,String cookieName ){
		return CookieUtil.getValue(request, cookieName);
	}
	public boolean validateUIApis(HttpServletRequest request,String cookieName ,UserDetails userDetails){
		try{
		String token=getTokenFromCookie(request,cookieName);
		String username= getUserNameFromToken(token);
		System.out.println("Token in validateUIApis \n"+token);
		System.out.println("Username in validateUIApis ==>"+username);
		return userDetails.getUsername().equalsIgnoreCase(username)&& isTokenExpired(token);
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}
		
	}
	
}
