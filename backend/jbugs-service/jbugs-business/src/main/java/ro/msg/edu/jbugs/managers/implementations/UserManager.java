package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.NotificationDao;
import dao.RoleDao;
import dao.UserDao;
import entity.Notification;
import entity.Role;
import entity.User;
import entity.enums.NotificationType;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    private NotificationDao notificationDao;

    @EJB
    private RoleDao roleDao;


    public UserDTO insertUser(UserDTO userDTO){
        String generatedUsername = generateUsername(userDTO.getFirstName(), userDTO.getLastName());
        userDTO.setUsername(generatedUsername);

        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();
        userDTO.setPassword(hashedPassword);
        User userToBeInserted = UserDTOEntityMapper.getUserFromUserDto(userDTO);

        userToBeInserted.setCounter(0);
        userToBeInserted.setStatus(false);


        User insertedUser = userDao.insertUser(userToBeInserted);

        Notification welcomeNotification = new Notification();
        welcomeNotification.setMessage("Welcome: " + insertedUser.getUsername());
        String date = LocalDateTime.now().toString();
        welcomeNotification.setDate(date);
        welcomeNotification.setType(NotificationType.WELCOME_NEW_USER);
        welcomeNotification.setUser(insertedUser);
        notificationDao.insertNotification(welcomeNotification);
        Set<Notification> notifications = insertedUser.getNotifications();
        notifications.add(welcomeNotification);
        insertedUser.setNotifications(notifications);

        Set<Role> roles = insertedUser.getRoles();

        for (Integer roleId : userDTO.getRoleIds()) {
            Role role = roleDao.findRole(roleId);
            roles.add(role);
        }

        insertedUser.setRoles(roles);
        return UserDTOEntityMapper.getDtoFromUser(insertedUser);
    }

    public UserDTO findAUser(Integer id){
        User userToBeFound = userDao.findUser(id);
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(userToBeFound);
        return userDTO;
    }

    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAll();

        return users.stream().map(UserDTOEntityMapper :: getDtoFromUser).collect(Collectors.toList());
    }

    public Long findCreatedBugs(UserDTO userDTO){
        User user = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        Long numberOfCreatedBugs = userDao.getCreatedBugs(user);
        return numberOfCreatedBugs;
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String firstPart;
        if (lastName.length() >= 5){
            firstPart = lastName.substring(0, 5);
        }
        else {
            firstPart = lastName;
        }
        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();
        username = username.replaceAll("\\s", "");
        username = username.replaceAll("\\W", "");
        while(!userDao.checkUsernameUnique(username)){
            charPosition++;
            if(charPosition < firstName.length()) {
                username = (username + (firstName.charAt(charPosition))).toLowerCase();
            } else {
                username = username + "x";
            }
        }
        return username;
    }

    public UserDTO login(String username, String password) throws BusinessException {
        User user = new User();
        try {
            user = userDao.findUserByUsername(username);
            if (user == null) {
                return null;
            }
            if (user.isStatus() == false) {
                try {
                    user = userDao.findUserByUsernameAndPassword1(username, password);
                    user.setCounter(0);
                } catch (BusinessException e) {
                    e.printStackTrace();
                    user.setCounter(user.getCounter() + 1);
                    if (user.getCounter() > 5)
                        user.setStatus(false);
                    return null;
                }
            } else {
                System.out.println("Account is blocked!");
                return null;
            }
        } catch (BusinessException e) {
            e.printStackTrace();

        }
        return UserDTOEntityMapper.getDtoFromUser(user);
    }


}
