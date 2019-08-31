package ro.msg.edu.jbugs.managers.implementations;

import com.google.common.hash.Hashing;
import dao.NotificationDao;
import dao.UserDao;
import entity.Notification;
import entity.Role;
import entity.User;
import entity.enums.NotificationType;
import exceptions.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationManagerTest {

    @InjectMocks
    private NotificationManager notificationManager;

    @Mock
    private NotificationDao notificationDao;

    @Mock
    private UserDao userDao;

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

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);

        return user;
    }

    public Notification createNotification() {
        User user = createUser();
        Notification notif = new Notification();
        notif.setId(1);
        notif.setMessage("New notification.");
        notif.setType(NotificationType.WELCOME_NEW_USER);
        notif.setUrl("www.bug.com");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        notif.setDate(date);
        notif.setUser(user);

        return notif;
    }

    @Test
    public void insertNotification() throws BusinessException {
        Notification notification = createNotification();

        when(userDao.findUser(notification.getUser().getId())).thenReturn(notification.getUser());
        when(notificationDao.insertNotification(any(Notification.class))).thenReturn(notification);

        Notification insertedNotification = notificationManager.insertNotification(notification, notification.getUser().getId());
        assertEquals(insertedNotification.getId(), notification.getId());
        assertEquals(insertedNotification.getMessage(), notification.getMessage());
        assertEquals(insertedNotification.getDate(), notification.getDate());
        assertEquals(insertedNotification.getUrl(), insertedNotification.getUrl());
        assertEquals(insertedNotification.getUser(), insertedNotification.getUser());
    }

    @Test
    public void findAllNotifications() {
        List<Notification> notifs = new ArrayList<>();
        Notification notif = createNotification();
        Notification notif2 = notif;
        notif2.setId(2);

        notifs.add(notif);
        notifs.add(notif2);

        when(notificationDao.findAllNotifications()).thenReturn(notifs);

        assertEquals(2, notificationManager.findAllNotifications().size());
    }

    @Test(expected = BusinessException.class)
    public void findAllNotificationsByUserWithNoUserInDb() throws BusinessException {
        when(userDao.findUserByUsername("wijnag")).thenReturn(null);
        notificationManager.findAllNotificationsByUser("wijnag");
    }

    @Test
    public void findAllNotificationsByUser() throws BusinessException {
        List<Notification> notifs = new ArrayList<>();
        Notification notif = createNotification();
        Notification notif2 = notif;
        notif2.setId(2);

        notifs.add(notif);
        notifs.add(notif2);
        User user = notif.getUser();

        when(userDao.findUserByUsername(user.getUsername())).thenReturn(user);
        when(notificationDao.findAllNotificationsByUser(any(User.class))).thenReturn(notifs);
        assertEquals(2, notificationManager.findAllNotificationsByUser(user.getUsername()).size());
    }
}