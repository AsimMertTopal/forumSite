package questapp.forumSite.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;
import questapp.forumSite.entities.User;
import questapp.forumSite.request.UserRequest;
import questapp.forumSite.security.JwtTokenProvider;
import questapp.forumSite.services.UserService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	
    @Autowired
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	

	@PostMapping("/login") 
	public String login(@RequestBody UserRequest loginRequest) {		
			 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			            loginRequest.getUserName(), loginRequest.getPassword());
			        Authentication auth = authenticationManager.authenticate(authToken);
			        System.out.println(auth);
			        SecurityContextHolder.getContext().setAuthentication(auth);
			        String jwtToken = jwtTokenProvider.generateJwtToken(auth);		       
			        return "Bearer " + jwtToken;
		
		
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest registerRequest) {
		
	    if (registerRequest.getUserName() == null || registerRequest.getPassword() == null) {
	        return new ResponseEntity<String>("Şifre eksik veya geçersiz", HttpStatus.BAD_REQUEST);
	    }

	    if (userService.getOneUserByName(registerRequest.getUserName()) != null)
	        return new ResponseEntity<String>("Kullanıcı adı Mevcut", HttpStatus.BAD_REQUEST);

	    User user = new User();
	    user.setUserName(registerRequest.getUserName());
	    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
	    userService.saveOneUser(user);

	    return new ResponseEntity<String>("Kullanıcı Oluşturuldu", HttpStatus.CREATED);
	}

}
