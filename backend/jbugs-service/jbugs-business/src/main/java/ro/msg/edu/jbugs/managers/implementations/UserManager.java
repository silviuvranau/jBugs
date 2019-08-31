package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.RoleDao;
import dao.UserDao;
import dao.NotificationDao;
import entity.Role;
import entity.User;
import entity.enums.NotificationType;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;
import ro.msg.edu.jbugs.util.NotificationUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
@Interceptors(Interceptor.class)
public class UserManager implements UserManagerRemote {
    @EJB
    private UserDao userDao;

    @EJB
    private RoleDao roleDao;

    @EJB
    private NotificationDao notificationDao;

    @EJB
    private NotificationUtils notificationUtils;

    @Override
    public UserDTO insertUser(UserDTO userDTO) {
        String generatedUsername = generateUsername(userDTO.getFirstName(), userDTO.getLastName());
        userDTO.setUsername(generatedUsername);

        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();
        userDTO.setPassword(hashedPassword);
        User userToBeInserted = UserDTOEntityMapper.getUserFromUserDto(userDTO);

        //user is not blocked
        userToBeInserted.setCounter(0);
        userToBeInserted.setStatus(false);

        userToBeInserted.setEmail(userToBeInserted.getEmail().toLowerCase());

        User insertedUser = userDao.insertUser(userToBeInserted);

        //send ntoification to the new user
        notificationUtils.sendNotification("", insertedUser, NotificationType.WELCOME_NEW_USER, "Welcome: " + insertedUser.getUsername());

        Set<Role> roles = insertedUser.getRoles();

        for (Integer roleId : userDTO.getRoleIds()) {
            Role role = roleDao.findRole(roleId);
            roles.add(role);
        }
        insertedUser.setRoles(roles);

        return UserDTOEntityMapper.getDtoFromUser(insertedUser);
    }

    @Override
    public UserDTO findAUser(Integer id) throws BusinessException {
        User userToBeFound = userDao.findUser(id);
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(userToBeFound);
        return userDTO;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userDao.findAll();

        return users.stream().map(UserDTOEntityMapper::getDtoFromUser).collect(Collectors.toList());
    }

    /**
     * Get how many bugs a User created.
     *
     * @param userDTO
     * @return
     */
    @Override
    public Long findCreatedBugs(UserDTO userDTO) {
        User user = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        Long numberOfCreatedBugs = userDao.getCreatedBugs(user);
        return numberOfCreatedBugs;
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    @Override
    public UserDTO modifyUser(UserDTO userDTO) throws BusinessException {
        User user = userDao.findUser(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());

        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();

        user.setPassword(hashedPassword);

        Set<Role> roles = new HashSet<>();
        for (Integer roleId : userDTO.getRoleIds()) {
            Role role = roleDao.findRole(roleId);
            roles.add(role);
        }
        user.setRoles(roles);

        return UserDTOEntityMapper.getDtoFromUser(user);
    }

    /**
     * Generate username for User:
     * first 5 letters from lastName
     * first letter from firstName:
     * if exists => take next letters
     * if letters run out => put x
     *
     * @param firstName
     * @param lastName
     * @return
     */
    @Override
    public String generateUsername(String firstName, String lastName) {
        String firstPart;
        if (lastName.length() >= 5) {
            firstPart = lastName.substring(0, 5);
        } else {
            firstPart = lastName;
        }
        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();
        username = username.replaceAll("\\s", "");
        username = username.replaceAll("\\W", "");
        while (!userDao.checkUsernameUnique(username)) {
            charPosition++;
            if (charPosition < firstName.length()) {
                username = (username + (firstName.charAt(charPosition))).toLowerCase();
            } else {
                username = username + "x";
            }
        }
        return username;
    }

    /**
     * Checks if user exista in database,
     *        if login credentials are AND
     *        if user is blocked or not
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    public UserDTO login(String username, String password) throws BusinessException {
        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        User user = userDao.findUserByUsername(username);

        if (user == null) {
            throw new BusinessException("msg_005","User not found !");
        }

        //checks if user is blocked
        if ( !user.isStatus() ) {
            try {
                user = userDao.findUserByUsernameAndPassword(username, hashedPassword);
                user.setCounter(0);
            } catch (BusinessException e) {
                user.setCounter(user.getCounter() + 1);
                if (user.getCounter() > 4){
                    //block (deactivate) user
                    user.setStatus(true);
                }
                throw new BusinessException("msg_007", "Invalid credentials");
            }
        } else {
            throw new BusinessException("msg_006", "Account is blocked !");
        }

        return UserDTOEntityMapper.getDtoFromUser(user);
    }


}
