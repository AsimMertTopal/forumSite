package questapp.forumSite.security;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {

	private SecretKey APP_SECRET;
	
	public JwtTokenProvider() {
		try {
		    KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
		    this.APP_SECRET = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
	}

	@Value("${questapp.expires.in}")
	private Long EXPIRES_IN;

	public String generateJwtToken(Authentication auth) {
		JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().subject(Long.toString(userDetails.getId())).issuedAt(new Date()).expiration(expireDate)
				  .signWith(APP_SECRET, Jwts.SIG.HS512) 
				    .compact();
	}

	Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().verifyWith(APP_SECRET).build().parseSignedClaims(token).getPayload();
		return Long.parseLong(claims.getSubject());
	}

	boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(APP_SECRET).build().parseSignedClaims(token);
			return !isTokenExpired(token);
		} catch (SignatureException e) {
			return false;
		} catch (MalformedJwtException e) {
			return false;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
        Date expiration=Jwts.parser().verifyWith(APP_SECRET).build().parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
	}
}
