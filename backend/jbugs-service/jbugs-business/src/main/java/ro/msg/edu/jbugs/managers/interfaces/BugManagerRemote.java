package ro.msg.edu.jbugs.managers.interfaces;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface BugManagerRemote {
    void insertBug(BugDTO bugDTO);
    BugDTO findABug(Integer id);
    List<BugDTO> findAllBugs();
    List<BugDTO> findBugsByCreatedId(UserDTO userDTO);
    List<BugDTO> findBugsByAssignedId(UserDTO userDTO);
    Integer deleteExceedingBugs();
}