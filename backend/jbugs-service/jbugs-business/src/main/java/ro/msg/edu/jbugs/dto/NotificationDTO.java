package ro.msg.edu.jbugs.dto;

import entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class NotificationDTO implements Serializable {
    private Integer ID;
    private Date date;
    private String message;
    private String type;
    private String url;
    private User userID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "ID=" + ID +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", userID=" + userID +
                '}';
    }
}
