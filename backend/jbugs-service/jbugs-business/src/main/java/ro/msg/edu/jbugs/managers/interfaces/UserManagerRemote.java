package ro.msg.edu.jbugs.managers.interfaces;

import exceptions.BusinessException;
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
public interface UserManagerRemote {
    UserDTO insertUser(UserDTO userDTO);
    UserDTO findAUser(Integer id) throws BusinessException;
    List<UserDTO> findAllUsers();
    Long findCreatedBugs(UserDTO userDTO);
    Integer deleteUser(Integer id);
    String generateUsername(String firstName, String lastName) throws Exception;
    UserDTO login(String username, String password) throws BusinessException;
    UserDTO modifyUser(UserDTO userDTO) throws BusinessException;
}
