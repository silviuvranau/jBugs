package ro.msg.edu.jbugs.dto;

import entity.Bug;
import entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class CommentDTO implements Serializable {
    private Integer ID;
    private String text;
    private Date date;
    private User user;
    private Bug bug;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "ID=" + ID +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", bug=" + bug +
                '}';
    }
}
