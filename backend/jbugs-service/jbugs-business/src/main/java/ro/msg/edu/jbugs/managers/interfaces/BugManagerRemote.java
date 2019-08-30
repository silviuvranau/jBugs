package ro.msg.edu.jbugs.managers.interfaces;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
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
    BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO bugAttachmentWrapperDTO) throws BusinessException;
    BugDTO findABug(Integer id);
    List<BugDTO> findAllBugs();
    List<BugDTO> findBugsByCreatedId(UserDTO userDTO);
    List<BugDTO> findBugsByAssignedId(UserDTO userDTO);
    Integer deleteExceedingBugs();

    boolean canDeactivateUser(UserDTO userDTO) throws BusinessException;

    BugAttachmentWrapperDTO updateBug(BugAttachmentWrapperDTO bugAttWrapper, String username) throws BusinessException;
}
