package questapp.forumSite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import questapp.forumSite.dataAccess.LikeRepository;
import questapp.forumSite.entities.Like;
import questapp.forumSite.entities.Post;
import questapp.forumSite.entities.User;
import questapp.forumSite.request.LikeCreateRequest;

@Service
@AllArgsConstructor
public class LikeService {

	LikeRepository likeRepository;
	UserService userService;
	PostService postService;

	public List<Like> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
		if (userId.isPresent() && postId.isPresent()) {
			return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			return likeRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			return likeRepository.findByPostId(postId.get());
		} else
			return likeRepository.findAll();
	}

	public Like createOneLike(LikeCreateRequest request) {
	   User user=userService.getOneUser(request.getUserId());
	   Post post=postService.getOnePostById(request.getPostId());
	   if(user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setPost(post);
			likeToSave.setUser(user);
			return likeRepository.save(likeToSave);
		}else		
			return null;
	}

	public Like getOneLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);

	}
}
