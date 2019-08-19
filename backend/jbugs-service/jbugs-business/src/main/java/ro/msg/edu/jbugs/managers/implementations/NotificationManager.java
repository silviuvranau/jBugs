package ro.msg.edu.jbugs.managers.implementations;

import dao.NotificationDao;
import entity.Notification;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.NotificationManagerRemote;
import ro.msg.edu.jbugs.mappers.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
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
public class NotificationManager implements NotificationManagerRemote {
    @EJB
    NotificationDao notificationDao;
    @Override
    public Notification insertNotification(Notification notification) {
        notificationDao.insertNotification(notification);
        return notification;
    }

    @Override
    public List<NotificationDTO> findAllNotifications() {
        List<Notification> notifications = notificationDao.findAllNotifications();
        //return null;
        return notifications.stream().map(NotificationDTOEntityMapper:: getDtoFromNotification).collect(Collectors.toList());
    }
}
