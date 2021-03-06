package ro.msg.edu.jbugs.managers.interfaces;

import entity.Notification;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface NotificationManagerRemote {
    Notification insertNotification(Notification notification, Integer userId) throws BusinessException;
    List<NotificationDTO> findAllNotifications();
    List<NotificationDTO> findAllNotificationsByUser(String username) throws  BusinessException;
}
