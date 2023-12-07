package questapp.forumSite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import questapp.forumSite.dataAccess.CommentRepository;
import questapp.forumSite.entities.Comment;
import questapp.forumSite.entities.Post;
import questapp.forumSite.entities.User;
import questapp.forumSite.request.CommentCreateRequest;
import questapp.forumSite.request.CommentUpdateRequest;

@Service
@AllArgsConstructor
public class CommentService {
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public List<Comment> getAllComentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		if (userId.isPresent() && postId.isPresent()) {
			return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			return commentRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			return commentRepository.findByPostId(postId.get());
		} else
			return commentRepository.findAll();

	}

	public Comment getOneComments(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest commentCreateRequest) {
		User user = userService.getOneUser(commentCreateRequest.getUserId());
		Post post = postService.getOnePostById(commentCreateRequest.getPostId());
		if (user != null && post != null) {
			Comment comment = new Comment();
			comment.setId(commentCreateRequest.getId());
			comment.setPost(post);
			comment.setUser(user);
			comment.setText(commentCreateRequest.getText());
			return commentRepository.save(comment);

		} else
			return null;
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isPresent()) {
			Comment commentUpdate = comment.get();
			commentUpdate.setText(request.getText());
			return commentRepository.save(commentUpdate);

		} else
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
		
	}

}
