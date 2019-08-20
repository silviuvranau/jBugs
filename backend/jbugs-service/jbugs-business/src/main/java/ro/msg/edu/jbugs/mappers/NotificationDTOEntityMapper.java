package ro.msg.edu.jbugs.mappers;

import entity.Notification;
import ro.msg.edu.jbugs.dto.NotificationDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class NotificationDTOEntityMapper {
    private NotificationDTOEntityMapper(){}

    public static Notification getNotificationFromDto(NotificationDTO notificationDTO){
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setDate(notificationDTO.getDate());
        notification.setType(notificationDTO.getType());
        notification.setUrl(notificationDTO.getUrl());
        notification.setUser(UserDTOEntityMapper.getUserFromUserDto(notificationDTO.getUser()));

        return notification;
    }

    public static NotificationDTO getDtoFromNotification(Notification notification){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setDate(notification.getDate());
        notificationDTO.setType(notification.getType());
        notificationDTO.setUrl(notification.getUrl());
        notificationDTO.setUser(UserDTOEntityMapper.getDtoFromUser(notification.getUser()));

        return notificationDTO;
    }
}
