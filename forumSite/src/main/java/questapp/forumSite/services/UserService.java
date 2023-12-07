package questapp.forumSite.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import questapp.forumSite.dataAccess.CommentRepository;
import questapp.forumSite.dataAccess.LikeRepository;
import questapp.forumSite.dataAccess.PostRepository;
import questapp.forumSite.dataAccess.RoleRepository;
import questapp.forumSite.dataAccess.UserRepository;
import questapp.forumSite.entities.Role;
import questapp.forumSite.entities.RoleName;
import questapp.forumSite.entities.User;

@Service
public class UserService {
	
	
	UserRepository userRepository;
	LikeRepository likeRepository;
	CommentRepository commentRepository;
	PostRepository postRepository;
	RoleRepository roleRepository;
    
	@Autowired
	public UserService(UserRepository userRepository, LikeRepository likeRepository, 
			CommentRepository commentRepository, PostRepository postRepository,RoleRepository roleRepository) {

		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.roleRepository = roleRepository;
	}
	



	


	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User add(User newUser) {
//		User user=new User();
//		newUser.setUserName(user.getUserName());
//		newUser.setPassword(user.getPassword());
		return userRepository.save(newUser);
	}

	public User getOneUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	
	
	public User updateOneUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			userRepository.save(foundUser);
			return foundUser;

		} else
			return null;
	}

	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	public User getOneUserByName(String userName) {
		
		return userRepository.findByUserName(userName);
	}

	public void saveOneUser(User user) {
      userRepository.save(user);		
	}
	
	public void createUserWithRole() {
	    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	            .orElseThrow(() -> new RuntimeException("User Role not found"));

	    User user = new User();
	    Set<Role> roles = new HashSet<>();
	    roles.add(userRole);
	    user.setRoles(roles);

	    userRepository.save(user);
	}


}
