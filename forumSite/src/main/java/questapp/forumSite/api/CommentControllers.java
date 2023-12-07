package questapp.forumSite.api;

import java.util.List;
import java.util.Optional;

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
import questapp.forumSite.services.CommentService;
import questapp.forumSite.entities.Comment;
import questapp.forumSite.request.CommentCreateRequest;
import questapp.forumSite.request.CommentUpdateRequest;
@CrossOrigin
@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentControllers {
	private CommentService commentService;

	@GetMapping("/getAll")
	public List<Comment> getAllComents(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {

		return commentService.getAllComentsWithParam(userId, postId);
	}

	@GetMapping("/{commentId}")
	public Comment getOneComments(@PathVariable Long commentId) {
		return commentService.getOneComments(commentId);
	}

	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest commentCreateRequest) {

		return commentService.createOneComment(commentCreateRequest);

	}

	@PutMapping("/{commentId}")
	public Comment update(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
		return commentService.updateOneCommentById(commentId, request);
	}

	@DeleteMapping("/{commentId}")
	public void deleteOneComment(@PathVariable Long commentId) {
		commentService.deleteOneCommentById(commentId);
	}
}
