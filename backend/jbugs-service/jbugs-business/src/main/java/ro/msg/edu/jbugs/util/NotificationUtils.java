package ro.msg.edu.jbugs.util;

import dao.NotificationDao;
import dao.UserDao;
import entity.Notification;
import entity.User;
import entity.enums.NotificationType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class NotificationUtils {

    @EJB
    private UserDao userDao;

    @EJB
    NotificationDao notificationDao;

    public void sendNotification(User user, NotificationType type, String message) {
        Notification updateNotification = new Notification();
        updateNotification.setMessage(message);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        updateNotification.setDate(date);
        updateNotification.setType(type);
        updateNotification.setUser(user);
        notificationDao.insertNotification(updateNotification);
        Set<Notification> notifications = user.getNotifications();
        notifications.add(updateNotification);
        user.setNotifications(notifications);
    }
}
