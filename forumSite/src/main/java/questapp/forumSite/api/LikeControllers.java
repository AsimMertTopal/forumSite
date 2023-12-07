package questapp.forumSite.api;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import questapp.forumSite.entities.Like;
import questapp.forumSite.request.LikeCreateRequest;
import questapp.forumSite.services.LikeService;
@CrossOrigin
@RestController
@RequestMapping("api/like")
@AllArgsConstructor
public class LikeControllers {
private LikeService likeService;

@GetMapping
public List<Like> getAllLikes(@RequestParam Optional<Long> userId, 
		@RequestParam Optional<Long> postId) {
	return likeService.getAllLikesWithParam(userId, postId);
}

@PostMapping
public Like createOneLike(@RequestBody LikeCreateRequest request) {
	return likeService.createOneLike(request);
}

@GetMapping("/{likeId}")
public Like getOneLike(@PathVariable Long likeId) {
	return likeService.getOneLikeById(likeId);
}

@DeleteMapping("/{likeId}")
public void deleteOneLike(@PathVariable Long likeId) {
	likeService.deleteOneLikeById(likeId);
}
}

