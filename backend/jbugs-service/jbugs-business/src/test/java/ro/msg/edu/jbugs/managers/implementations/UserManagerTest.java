package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.RoleDao;
import dao.UserDao;
import entity.Role;
import entity.User;
import entity.enums.NotificationType;
import exceptions.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;
import ro.msg.edu.jbugs.util.NotificationUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {
    @InjectMocks
    private UserManager userManager;


    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private NotificationUtils notificationUtils;

    public UserManagerTest() {
        this.userManager = new UserManager();
        this.userDao = new UserDao();
    }

    private User createUser() {

        User user = new User();
        user.setId(1);
        user.setFirstName("Gini");
        user.setLastName("Wijnaldum");
        user.setEmail("giniwijnaldum@msggroup.com");
        user.setCounter(0);
        user.setMobileNumber("+40754498876");
        user.setUsername("wijnag");

        String hashedPassword = Hashing.sha256()
                .hashString("test", StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashedPassword);

        Role role = new Role();
        role.setId(1);
        role.setType("ADM");

        return user;
    }

    @Test
    public void generateUsername() {
        when(userDao.checkUsernameUnique("pricos")).thenReturn(true);
        assertEquals("pricos", userManager.generateUsername("Stefania", "Pricop"));
        assertNotEquals("pricost", userManager.generateUsername("Stefania", "Pricop"));
    }

    @Test
    public void checkUsernameUnique() {
        when(userDao.checkUsernameUnique("baloz")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozs")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozso")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsol")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsolt")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsoltx")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsoltxx")).thenReturn(true);

        assertEquals("balozsoltxx", userManager.generateUsername("Zsolt", "Balo"));
    }

    //user not in database
     @Test(expected = BusinessException.class)
     public void login() throws BusinessException {
         User user = createUser();
         when(userDao.findUserByUsername(user.getUsername())).thenReturn(null);
         userManager.login(user.getUsername(), user.getPassword());
     }

    //User deactivated
    @Test(expected = BusinessException.class)
    public void login2() throws BusinessException {
        User user = createUser();
        user.setStatus(true);
        when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        when(userDao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        userManager.login(user.getUsername(), user.getPassword());
    }

    //wrong password
    @Test(expected = BusinessException.class)
    public void login3() throws BusinessException {
        User user = createUser();
        String hashedPassword = Hashing.sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        when(userDao.findUserByUsernameAndPassword(user.getUsername(), hashedPassword)).thenThrow(BusinessException.class);
        userManager.login(user.getUsername(), user.getPassword());
    }

    //login with good login credentials
    @Test
    public void login4() throws BusinessException {
        User user = createUser();
        String hashedPassword = Hashing.sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        when(userDao.findUserByUsernameAndPassword(user.getUsername(), hashedPassword)).thenReturn(user);
        UserDTO loggedUser = userManager.login(user.getUsername(), user.getPassword());

        assertEquals(user.getFirstName(), loggedUser.getFirstName());
        assertEquals(user.getLastName(), loggedUser.getLastName());
        assertEquals(user.getUsername(), loggedUser.getUsername());
        assertEquals(user.getCounter(), loggedUser.getCounter());
        assertEquals(user.getEmail(), loggedUser.getEmail());
        assertEquals(user.getMobileNumber(), loggedUser.getMobileNumber());
        assertEquals(user.isStatus(), loggedUser.isStatus());
    }

    @Test
    public void insertUser() {
        User user = createUser();
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(user);
        userDTO.setPassword("test");
        userDTO.setUsername("");

        Role role = new Role();
        role.setId(1);
        role.setType("ADM");

        when(userDao.checkUsernameUnique("wijnag")).thenReturn(true);
        when(roleDao.findRole(1)).thenReturn(role);

        when(userDao.insertUser(any(User.class))).thenReturn(user);

        NotificationUtils notificationUtils = mock(NotificationUtils.class);
        doNothing().when(notificationUtils).sendNotification("", isA(User.class), isA(NotificationType.class), eq("Welcome: " + user.getUsername()));

        UserDTO insertedUser = userManager.insertUser(userDTO);

        assertEquals(insertedUser.getFirstName(), "Gini");
        assertEquals(insertedUser.getLastName(), "Wijnaldum");
        assertEquals(insertedUser.getEmail(), "giniwijnaldum@msggroup.com");
        assertEquals(insertedUser.getCounter(), Integer.valueOf(0));
        assertEquals(userDTO.getMobileNumber(), "+40754498876");
        assertEquals(insertedUser.getPassword(), user.getPassword());
        assertEquals(insertedUser.getUsername(), "wijnag");
        //assertEquals(insertedUser.getRoleIds().get(0), Integer.valueOf(1));

    }

    @Test
    public void findAUser() throws BusinessException {
        User user = createUser();

        when(userDao.findUser(user.getId())).thenReturn(user);
        UserDTO userDTO = userManager.findAUser(user.getId());

        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getCounter(), user.getCounter());
        assertEquals(userDTO.getMobileNumber(), user.getMobileNumber());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getUsername(), user.getUsername());
    }

    @Test
    public void findAllUsers() {
        List<User> userList = new ArrayList<>();
        User user = createUser();
        User user2 = user;
        user2.setId(2);
        userList.add(user);
        userList.add(user2);

        when(userDao.findAll()).thenReturn(userList);
        assertEquals(2, userManager.findAllUsers().size());
    }

    @Test
    public void findCreatedBugs() {
        User user = createUser();
        when(userDao.getCreatedBugs(any(User.class))).thenReturn((long) 3);
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(user);
        Long number = userManager.findCreatedBugs(userDTO);

        assertEquals((long) number, (long) 3);
    }

    @Test
    public void deleteUser() {
        User user = createUser();
        when(userDao.deleteUser(user.getId())).thenReturn(user.getId());

        assertEquals(user.getId(), userManager.deleteUser(user.getId()));
        assertNotEquals(13, (int) userManager.deleteUser(user.getId()));
    }

    @Test
    public void modifyUser() throws BusinessException {
        User user = createUser();
        User newUser = user;
        user.setStatus(true);

        when(userDao.findUser(newUser.getId())).thenReturn(user);

        UserDTO userDTO = userManager.modifyUser(UserDTOEntityMapper.getDtoFromUser(newUser));

        assertEquals(userDTO.getFirstName(), newUser.getFirstName());
        assertEquals(userDTO.getLastName(), newUser.getLastName());
        assertEquals(userDTO.getEmail(), newUser.getEmail());
        assertEquals(userDTO.getCounter(), newUser.getCounter());
        assertEquals(userDTO.getMobileNumber(), newUser.getMobileNumber());
        assertEquals(userDTO.getPassword(), newUser.getPassword());
        assertEquals(userDTO.getUsername(), newUser.getUsername());
    }

}
