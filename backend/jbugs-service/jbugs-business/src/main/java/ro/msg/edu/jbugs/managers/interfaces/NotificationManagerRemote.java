package ro.msg.edu.jbugs.managers.interfaces;

import entity.Notification;
import ro.msg.edu.jbugs.dto.NotificationDTO;

import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public interface NotificationManagerRemote {
    Notification insertNotification(Notification notification, Integer userId);
    List<NotificationDTO> findAllNotifications();
}