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
@Table(name="comments")
@NamedQueries({
        @NamedQuery(name= Comment.DELETE_COMMENTS, query = "DELETE FROM Comment c " +
                "WHERE c.date <= :date")
})
public class Comment implements Serializable {
    public static final String DELETE_COMMENTS = "deleteComments";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name="text")
    private String text;

    @Column(name="date")
    private String date;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "ID")

    private User user;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bug;

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

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", bug=" + bug +
                '}';
    }
}
