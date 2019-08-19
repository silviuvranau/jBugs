package ro.msg.edu.jbugs.dto;

import entity.User;
import entity.enums.NotificationType;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class NotificationDTO implements Serializable {
    private Integer ID;
    private String date;
    private String message;
    private NotificationType type;
    private String url;
    private User user;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "ID=" + ID +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", user=" + user +
                '}';
    }
}
