package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.managers.interfaces.NotificationManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/notifications")
@Produces("application/json")
public class NotificationController {

    @EJB
    private NotificationManagerRemote notificationManager;

    @GET
    public List<NotificationDTO> getAllNotifications() {
        return notificationManager.findAllNotifications();
    }

    @GET
    @Path("/{username}")
    public List<NotificationDTO> getAllNotificationsByUser(@PathParam("username") String username) {
        try {
            return notificationManager.findAllNotificationsByUser(username);
        } catch (BusinessException e) {
            return null;
        }
    }
}
