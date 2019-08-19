package ro.msg.edu.jbugs.mappers;

import entity.Bug;
import ro.msg.edu.jbugs.dto.BugDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugDTOEntityMapper {
    public static Bug getBugFromDto(BugDTO bugDTO){
        Bug bug = new Bug();
        bug.setId(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setAssignedID(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getAssignedId()));
        bug.setCreatedID(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getCreatedId()));
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setStatus(bugDTO.getStatus());
        return bug;
    }

    public static BugDTO getDtoFromBug(Bug bug){
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getId());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity());
        bugDTO.setCreatedId(UserDTOEntityMapper.getDtoFromUser(bug.getCreatedID()));
        bugDTO.setAssignedId(UserDTOEntityMapper.getDtoFromUser(bug.getAssignedID()));
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());
        return bugDTO;
    }
}
