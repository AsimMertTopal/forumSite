package questapp.forumSite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import questapp.forumSite.entities.Post;
import questapp.forumSite.entities.User;
import questapp.forumSite.request.PostCreateRequest;
import questapp.forumSite.request.PostUpdateRequest;
import questapp.forumSite.dataAccess.PostRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {

	private PostRepository postRepository;
	private UserService userService;

	public List<Post> getAllPosts(Optional<Long> userId) {

		if (userId.isPresent())

			return postRepository.findByUserId(userId.get());
		return postRepository.findAll();
	}

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}

	public Post createOnePost(PostCreateRequest newPostRequest) {
		User user = userService.getOneUser(newPostRequest.getUserId());
		if (user == null) {
			return null;
		} else {
			Post post = new Post();
			post.setId(newPostRequest.getId());
			post.setText(newPostRequest.getText());
			post.setTitle(newPostRequest.getTitle());
			post.setUser(user);
			return postRepository.save(post);
		}
			
			
		
	}

	public void deleteOnePost(Long postId) {
		postRepository.deleteById(postId);
	}

	public Post updateOnePost(Long postId, PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(postUpdateRequest.getText());
			toUpdate.setTitle(postUpdateRequest.getTitle());
			postRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}
}