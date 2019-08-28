package entity;

import entity.enums.NotificationType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name="notifications")
@NamedQueries({
        @NamedQuery(name= Notification.FIND_ALL_NOTIFICATIONS, query = "select n from Notification n"),
        @NamedQuery(name= Notification.FIND_ALL_NOTIFICATIONS_BY_USER, query = "select n from Notification n where n.user = :user"),
})
public class Notification implements Serializable {
    public static final String FIND_ALL_NOTIFICATIONS = "findAllNotifications";
    public static final String FIND_ALL_NOTIFICATIONS_BY_USER = "findAllNotificationsByUser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="date")
    private String date;

    @Column(name="message")
    private String message;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name="url")
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Notification(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Notification{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", user=" +
                '}';
    }
}
