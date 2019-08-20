package ro.msg.edu.jbugs.mappers;

import entity.Comment;
import ro.msg.edu.jbugs.dto.CommentDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class CommentDTOEntityMapper {
    private CommentDTOEntityMapper(){}

    public static Comment getCommentFromDto(CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setDate(commentDTO.getDate());
        comment.setUser(commentDTO.getUser());
        comment.setBug(commentDTO.getBug());

        return comment;
    }

    public static CommentDTO getDtoFromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setDate(comment.getDate());
        commentDTO.setUser(comment.getUser());
        commentDTO.setBug(comment.getBug());

        return commentDTO;
    }
}
