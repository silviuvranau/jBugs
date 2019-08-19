package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.UserDao;
import entity.Notification;
import entity.User;
import entity.enums.NotificationType;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.NotificationManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
    private NotificationManagerRemote notificationManagerRemote;

//    public void insertUser(UserDTO userDTO){
//        User managers = UserDTOEntityMapper.getUserFromUserDto(userDTO);
//        userDao.insertUser(managers);
//    }

    public UserDTO insertUser(UserDTO userDTO){
        String username = "";

        try {
            username = generateUsername(userDTO.getFirstName(), userDTO.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDTO.setUsername(username);

        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();
        userDTO.setPassword(hashedPassword);

        User userToBeInserted = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        User insertedUser = userDao.insertUser(userToBeInserted);

        Notification notification = new Notification();
        notification.setMessage("Welcome: " + username);
        notification.setUser(UserDTOEntityMapper.getUserFromUserDto(userDTO));
        notification.setDate("");
        notification.setType(NotificationType.WELCOME_NEW_USER);
        notification.setDate("2000-01-09 01:10:20");
        notification.setType(NotificationType.BUG_CLOSED);

        notificationManagerRemote.insertNotification(notification, insertedUser.getId());
        return UserDTOEntityMapper.getDtoFromUser(insertedUser);
    }

    public UserDTO findAUser(Integer id){
        User userToBeFound = userDao.findUser(id);
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(userToBeFound);
        return userDTO;
    }

    public UserDTO findUser(Integer id){
        User user = userDao.findUser(id);
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(user);
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
    public String generateUsername(String firstName, String lastName) throws Exception {
        String firstPart;
        if (lastName.length() >= 5){
            firstPart = lastName.substring(0, 5);
        }
        else {
            firstPart = lastName;
        }
        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();
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

//    public UserDTO login(String username, String password) throws BusinessException {
//        return UserDTOEntityMapper.getDtoFromUser(userDao.findUserByUsernameAndPassword(username, password));
//    }

    public UserDTO login(String username, String password) throws BusinessException {
        User userToLogin = userDao.findUserByUsername(username);
        if (userToLogin != null) {
            String loggingPassword = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
            if (loggingPassword.equals(userToLogin.getPassword())) {
                if (userToLogin.isStatus() == false) {
                    if (!userToLogin.isStatus()) {
                        throw new BusinessException("Your account is disabled", "Throw");
                    } else {
                        userToLogin.setCounter(0);
                        return UserDTOEntityMapper.getDtoFromUser(userToLogin);
                    }
                } else {
                    Integer userCounter = userToLogin.getCounter() + 1;
                    userToLogin.setCounter(userCounter);
                    if (userCounter == 5) {
                        userToLogin.setStatus(false);
                        throw new BusinessException("Exceeding max login tries", "_");
                    }
                }
            } else {
                throw new BusinessException("User does not exist in DB", "_");
            }
        }
        return null;
    }


}