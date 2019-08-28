package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.NotificationDao;
import dao.RoleDao;
import dao.UserDao;
import entity.Notification;
import entity.Role;
import entity.User;
import exceptions.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

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
    private NotificationDao notificationDao;

    public UserManagerTest() {
        this.userManager = new UserManager();
        this.userDao = new UserDao();
    }

    @Test
    public void generateUsername() throws Exception {
        when(userDao.checkUsernameUnique(anyString())).thenReturn(true);
        when(userDao.checkUsernameUnique("1")).thenReturn(true);
        //when(userDao.checkUsernameUnique("pricos")).thenReturn(false);

        assertTrue(userDao.checkUsernameUnique("1"));

        //assertEquals("1", userManager.generateUsername("123", "456"));
        assertEquals("pricos", userManager.generateUsername("Stefania", "Pricop"));

        //verify(userDao, times(1)).checkUsernameUnique(anyString());
    }

    //@Test(expected = Exception.class)
    @Test
    public void checkUsernameUnique() throws Exception {
        //when(userDao.checkUsernameUnique("pricos")).thenReturn(true);
        //assertEquals("pricos", userManager.generateUsername("Stefania", "Pricop"));

        when(userDao.checkUsernameUnique("baloz")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozs")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozso")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsol")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsolt")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsoltx")).thenReturn(false);
        when(userDao.checkUsernameUnique("balozsoltxx")).thenReturn(true);

        assertEquals("balozsoltxx", userManager.generateUsername("Zsolt", "Balo"));
    }

    /**
     @Test(expected = BusinessException.class)
     public void login() throws BusinessException {
     when(userDao.findUserByUsernameAndPassword("testt", "test")).thenThrow(BusinessException.class);
     userManager.login("testt", "test");
     }

     @Test public void login2() throws BusinessException {
     when(userDao.findUserByUsernameAndPassword("testt", "test")).thenReturn(createUser());
     UserDTO userDTO = userManager.login("testt", "test");
     assertEquals("testt", userDTO.getUsername());
     assertEquals("test", userDTO.getFirstName());
     assertEquals("test", userDTO.getLastName());

     }**/

    private User createUser(){
        User user = new User();
        user.setId(1);
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("_");
        user.setCounter(4);
        user.setMobileNumber("1");
        user.setUsername("testt");
        user.setPassword("test");

        return user;
    }

    /**
     @Test(expected = BusinessException.class)
     public void loginV2() throws BusinessException {
     when(userDao.findUserByUsername("testt")).thenReturn(null);
     UserDTO user = userManager.login("testt", "test");

     assertNull(user);
     }

     @Test public void loginThatReturnsNull() throws BusinessException{
     User persistedUser = createUser();
     when(userDao.findUserByUsername("testt")).thenReturn(persistedUser);
     UserDTO userToLogin = userManager.login("testt", "wrong");

     assertEquals((Integer)2, persistedUser.getCounter());
     }

     @Test public void loginThatHasRightPassword() throws BusinessException{
     User persistedUser = createUser();
     when(userDao.findUserByUsername("testt")).thenReturn(persistedUser);

     UserDTO userToLogin = userManager.login("testt", "test");
     assertEquals(persistedUser.getCounter(), userToLogin.getCounter());
     assertEquals(persistedUser.isStatus(), userToLogin.getStatus());
     }

     @Test(expected = BusinessException.class)
     public void loginThatExceedsCounter() throws BusinessException{
     User persistedUser = createUser();

     }**/


    @Test
    public void insertUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Gini");
        userDTO.setLastName("Wijnaldum");
        userDTO.setEmail("giniwijnaldum@msggroup.com");
        userDTO.setCounter(0);
        userDTO.setMobileNumber("+40754498876");
        userDTO.setPassword("test");
        List<Integer> roles = userDTO.getRoleIds();
        roles.add(1);
        userDTO.setRoleIds(roles);

        User user = UserDTOEntityMapper.getUserFromUserDto(userDTO);

//        when(userManager.generateUsername("Gini", "Wijnaldum")).thenReturn("wijnag");
        when(userDao.checkUsernameUnique("wijnag")).thenReturn(true);

        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();

        User returnedUser = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        returnedUser.setUsername("wijnag");
        returnedUser.setId(1);
        returnedUser.setPassword(hashedPassword);
        Role role = new Role();
        role.setId(1);
        role.setType("ADM");


        when(roleDao.findRole(1)).thenReturn(role);


        when(userDao.insertUser(any(User.class))).thenReturn(returnedUser);
        when(notificationDao.insertNotification(any(Notification.class)))
                .thenReturn(new Notification());

        UserDTO insertedUser = userManager.insertUser(userDTO);
//        UserDTO insertedUser = new UserDTO();

        assertEquals(insertedUser.getFirstName(), "Gini");
        assertEquals(insertedUser.getLastName(), "Wijnaldum");
        assertEquals(insertedUser.getEmail(), "giniwijnaldum@msggroup.com");
        assertEquals(insertedUser.getCounter(), Integer.valueOf(0));
        assertEquals(userDTO.getMobileNumber(), "+40754498876");
        assertEquals(insertedUser.getPassword(), hashedPassword);
        assertEquals(insertedUser.getUsername(), "wijnag");
        assertEquals(insertedUser.getRoleIds().get(0), Integer.valueOf(1));

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
