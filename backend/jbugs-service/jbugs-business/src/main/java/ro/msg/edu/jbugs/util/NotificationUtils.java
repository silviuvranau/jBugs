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

    public void sendNotification(String url, User user, NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        notification.setUrl(url);
        notification.setDate(date);
        notification.setType(type);
        notification.setUser(user);
        notificationDao.insertNotification(notification);
        Set<Notification> notifications = user.getNotifications();
        notifications.add(notification);
        user.setNotifications(notifications);
    }
}
