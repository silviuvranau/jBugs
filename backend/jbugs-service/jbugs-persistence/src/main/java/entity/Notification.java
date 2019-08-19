package entity;

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
})
public class Notification implements Serializable {
    public static final String FIND_ALL_NOTIFICATIONS = "findAllNotifications";

    @Column(name="date")
    private String date;

    @Column(name="message")
    private String message;

    @Column(name="type")
    private String type;

    @Column(name="url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user;

    public Notification(){}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public User getUserID() {
//        return userID;
//    }
//
//    public void setUserID(User userID) {
//        this.userID = userID;
//    }


    public User getUserID() {
        return user;
    }

    public void setUserID(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", userID=" + //userID +
                '}';
    }
}
