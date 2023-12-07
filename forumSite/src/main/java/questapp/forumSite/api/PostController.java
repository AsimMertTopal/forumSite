package questapp.forumSite.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

import questapp.forumSite.entities.Post;
import questapp.forumSite.request.PostCreateRequest;
import questapp.forumSite.request.PostUpdateRequest;
import questapp.forumSite.services.PostService;
@CrossOrigin
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
	
private PostService postService;

@PostMapping("/add")
public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
	return postService.createOnePost(newPostRequest);
			
}

@GetMapping("/getAll")
public List<Post> getAllPosts(@RequestParam Optional<Long> userId){		
	return postService.getAllPosts(userId);
}

@GetMapping("/{postId}")
public Post getOnePost(@PathVariable Long postId) {
	return postService.getOnePostById(postId);
			
}
@PutMapping("/{postId}")
public Post updateOnePost(@PathVariable Long postId,@RequestBody PostUpdateRequest postUpdateRequest) {
	return postService.updateOnePost(postId,postUpdateRequest);
}
@DeleteMapping("/{postId}")
public void  deleteOnePost(@PathVariable Long postId) {
	 postService.deleteOnePost(postId);
}
}
