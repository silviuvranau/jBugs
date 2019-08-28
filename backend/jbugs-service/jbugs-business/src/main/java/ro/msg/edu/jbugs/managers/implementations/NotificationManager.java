package ro.msg.edu.jbugs.managers.implementations;

import dao.NotificationDao;
import dao.UserDao;
import entity.Notification;
import entity.User;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
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
    private UserDao userDao;
    @EJB
    NotificationDao notificationDao;

    public Notification insertNotification(Notification notification, Integer userId) throws BusinessException {
        User user = userDao.findUser(userId);
        notification.setUser(user);
        notification = notificationDao.insertNotification(notification);
        return notification;
    }

    public List<NotificationDTO> findAllNotifications() {
        List<Notification> notifications = notificationDao.findAllNotifications();
        //return null;
        return notifications.stream().map(NotificationDTOEntityMapper:: getDtoFromNotification).collect(Collectors.toList());
    }

    public List<NotificationDTO> findAllNotificationsByUser(Integer id) throws BusinessException{
        //check if given id is valid
        User user = userDao.findUser(id);
        if (user != null){
            List<Notification> notifications = notificationDao.findAllNotificationsByUser(user);
            return notifications.stream().map(NotificationDTOEntityMapper:: getDtoFromNotification).collect(Collectors.toList());
        }
        else{
            throw new BusinessException("msg-001", "Given id is not coresponding to a user.");
        }
    }
}
