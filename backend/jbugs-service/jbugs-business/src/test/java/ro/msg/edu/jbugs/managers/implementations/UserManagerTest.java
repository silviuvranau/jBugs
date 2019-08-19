package ro.msg.edu.jbugs.managers.implementations;

import dao.UserDao;
import entity.User;
import exceptions.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.UserDTO;

import static org.junit.Assert.*;
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

    @Test(expected = BusinessException.class)
    public void login() throws BusinessException {
        when(userDao.findUserByUsernameAndPassword("testt", "test")).thenThrow(BusinessException.class);
        userManager.login("testt", "test");
    }

    @Test
    public void login2() throws BusinessException {
        when(userDao.findUserByUsernameAndPassword("testt", "test")).thenReturn(createUser());
        UserDTO userDTO = userManager.login("testt", "test");
        assertEquals("testt", userDTO.getUsername());
        assertEquals("test", userDTO.getFirstName());
        assertEquals("test", userDTO.getLastName());

    }

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

    @Test(expected = BusinessException.class)
    public void loginV2() throws BusinessException {
        when(userDao.findUserByUsername("testt")).thenReturn(null);
        UserDTO user = userManager.login("testt", "test");

        assertNull(user);
    }

    @Test
    public void loginThatReturnsNull() throws BusinessException{
        User persistedUser = createUser();
        when(userDao.findUserByUsername("testt")).thenReturn(persistedUser);
        UserDTO userToLogin = userManager.login("testt", "wrong");

        assertEquals((Integer)2, persistedUser.getCounter());
    }

    @Test
    public void loginThatHasRightPassword() throws BusinessException{
        User persistedUser = createUser();
        when(userDao.findUserByUsername("testt")).thenReturn(persistedUser);

        UserDTO userToLogin = userManager.login("testt", "test");
        assertEquals(persistedUser.getCounter(), userToLogin.getCounter());
        assertEquals(persistedUser.isStatus(), userToLogin.getStatus());
    }

    @Test(expected = BusinessException.class)
    public void loginThatExceedsCounter() throws BusinessException{
        User persistedUser = createUser();

    }
}