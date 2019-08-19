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
//    private Integer userID;
//    private Integer bugID;
    private User userID;
    private Bug bugID;

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
        return "CommentDTO{" +
                "text='" + text + '\'' +
                ", date=" + date +
                ", userID=" + userID +
                ", bugID=" + bugID +
                '}';
    }
}
