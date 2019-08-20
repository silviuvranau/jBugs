package ro.msg.edu.jbugs.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserDTO implements Serializable {
    private Integer id;
    private Integer counter;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String username;
    private String password;
    private boolean status;
    private Set<BugDTO> createdBugs;
    private Set<BugDTO> assignedBugs;
    private Set<CommentDTO> comments;
    private Set<RoleDTO> roles;
    private Set<NotificationDTO> notifications;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public Set<BugDTO> getCreatedBugs() {
        return createdBugs;
    }

    public void setCreatedBugs(Set<BugDTO> createdBugs) {
        this.createdBugs = createdBugs;
    }

    public Set<BugDTO> getAssignedBugs() {
        return assignedBugs;
    }

    public void setAssignedBugs(Set<BugDTO> assignedBugs) {
        this.assignedBugs = assignedBugs;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", counter=" + counter +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createdBugs=" + createdBugs +
                ", assignedBugs=" + assignedBugs +
                ", comments=" + comments +
                ", roles=" + roles +
                ", notifications=" + notifications +
                '}';
    }
}
