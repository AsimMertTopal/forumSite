package questapp.forumSite.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import questapp.forumSite.entities.User;
import questapp.forumSite.services.UserService;
@CrossOrigin
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllers {
	private UserService userService;

	@GetMapping("/getAll")
	public List<User> getAllUsers() {
		return userService.getAllUsers();

	}

	@PostMapping("/add")
	public User createUser(@RequestBody User newUser) {
		
		return userService.add(newUser);

	}

	@GetMapping("/{userId}")
	public User getOneUser(@PathVariable Long userId) {
		return userService.getOneUser(userId);
	}

	@PutMapping("/{userId}")
	public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
		return userService.updateOneUser(userId, newUser);
	}

	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		userService.deleteById(userId);
	}

}
