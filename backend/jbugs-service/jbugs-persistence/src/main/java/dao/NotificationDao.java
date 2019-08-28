package dao;

import entity.Notification;
import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class NotificationDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public NotificationDao(){}

    public Notification insertNotification(Notification notification){
        entityManager.persist(notification);
        entityManager.flush();
        //System.out.println(notification.getID());
        return notification;
    }

    public List<Notification> findAllNotifications(){
        Query query = entityManager.createNamedQuery(Notification.FIND_ALL_NOTIFICATIONS, Notification.class);
        List<Notification> notifis = query.getResultList();
        return notifis;
    }

    public List<Notification> findAllNotificationsByUser(User user) {
        Query query = entityManager.createNamedQuery(Notification.FIND_ALL_NOTIFICATIONS_BY_USER, Notification.class);
        query.setParameter("user", user);
        List<Notification> notifis = query.getResultList();
        return notifis;
    }
}
