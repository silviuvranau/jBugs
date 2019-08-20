package ro.msg.edu.jbugs.mappers;

import entity.Bug;
import entity.Comment;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.CommentDTO;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugDTOEntityMapper {
    public static Bug getBugFromDto(BugDTO bugDTO){
        Bug bug = new Bug();
        bug.setId(bugDTO.getId());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setAssignedId(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getAssignedId()));
        bug.setCreatedId(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getCreatedId()));
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setStatus(bugDTO.getStatus());

        List<Comment> comments = bugDTO.getComments().stream().map(CommentDTOEntityMapper::getCommentFromDto).collect(Collectors.toList());
        bug.setComments(new HashSet<Comment>(comments));

        return bug;
    }

    public static BugDTO getDtoFromBug(Bug bug){
        BugDTO bugDTO = new BugDTO();
        bugDTO.setId(bug.getId());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity());
        bugDTO.setCreatedId(UserDTOEntityMapper.getDtoFromUser(bug.getCreatedId()));
        bugDTO.setAssignedId(UserDTOEntityMapper.getDtoFromUser(bug.getAssignedId()));
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());

        List<CommentDTO> commentsDto = bug.getComments().stream().map(CommentDTOEntityMapper::getDtoFromComment).collect(Collectors.toList());
        bugDTO.setComments(new HashSet<CommentDTO>(commentsDto));

        return bugDTO;
    }
}
