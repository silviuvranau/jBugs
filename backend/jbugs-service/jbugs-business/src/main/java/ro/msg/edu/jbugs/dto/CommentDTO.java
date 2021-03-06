package ro.msg.edu.jbugs.dto;

import entity.Bug;
import entity.User;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class CommentDTO implements Serializable {
    private Integer id;
    private String text;
    private String date;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", bug=" + bug +
                '}';
    }
}
