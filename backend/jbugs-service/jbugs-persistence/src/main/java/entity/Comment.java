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

    private User userID;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bugID;

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

//    public Integer getUserID() {
//        return userID;
//    }
//
//    public void setUserID(Integer userID) {
//        this.userID = userID;
//    }
//
//    public Integer getBugID() {
//        return bugID;
//    }
//
//    public void setBugID(Integer bugID) {
//        this.bugID = bugID;
//    }


    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Bug getBugID() {
        return bugID;
    }

    public void setBugID(Bug bugID) {
        this.bugID = bugID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", date=" + date +
                ", userID=" + userID +
                ", bugID=" + bugID +
                '}';
    }
}
