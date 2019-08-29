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

    public static Bug getSearchedBugFromDto(BugDTO bugDTO, Bug bug) {
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

        return bug;
    }

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
        if (bug.getAssignedId() != null)
            bugDTO.setAssignedId(UserDTOEntityMapper.getDtoFromUser(bug.getAssignedId()));
        else
            bugDTO.setAssignedId(null);
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());

        return bugDTO;
    }
}
