package ro.msg.edu.jbugs.mappers;

import entity.Role;
import entity.User;
import ro.msg.edu.jbugs.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserDTOEntityMapper {

    private UserDTOEntityMapper(){}

    public static User getUserFromUserDto(UserDTO userDTO){
        User user = new User();
        if (userDTO != null){
            user.setId(userDTO.getId());
            user.setCounter(userDTO.getCounter());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setStatus(userDTO.getStatus());
            return user;
        }
        else{
            return null;
        }

    }

    public static UserDTO getDtoFromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setCounter(user.getCounter());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setStatus(user.isStatus());

        List<Integer> roleIds = new ArrayList<>();
        for (Role r : user.getRoles())
            roleIds.add(r.getId());
        userDTO.setRoleIds(roleIds);

        return userDTO;
    }

    public static User getSearchedUserFromUserDto(UserDTO userDTO, User user) {
        user.setId(userDTO.getId());
        user.setCounter(userDTO.getCounter());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setStatus(userDTO.getStatus());

        return user;
    }
}
