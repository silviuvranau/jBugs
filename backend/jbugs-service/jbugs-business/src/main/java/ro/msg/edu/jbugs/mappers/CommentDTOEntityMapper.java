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

    public Comment getCommentFromDto(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setID(commentDTO.getID());
        comment.setText(commentDTO.getText());
        comment.setDate(commentDTO.getDate());
        comment.setUserID(commentDTO.getUserID());
        comment.setBugID(commentDTO.getBugID());

        return comment;
    }

    public CommentDTO getDtoFromComment(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setID(comment.getID());
        commentDTO.setText(comment.getText());
        commentDTO.setDate(comment.getDate());
        commentDTO.setUserID(comment.getUserID());
        commentDTO.setBugID(comment.getBugID());

        return commentDTO;
    }


}
