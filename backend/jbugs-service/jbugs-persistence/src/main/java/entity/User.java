//package entity;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Set;
//
///**
// * Document me.
// *
// * @author msg systems AG; User Name.
// * @since 19.1.2
// */
//@Entity
//@Table(name="users")
//@NamedQueries({
//        @NamedQuery(name= User.FIND_ALL_USERS, query = "select u from User u")
//})
//public class User {
//    public static final String FIND_ALL_USERS = "findAllUsers";
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer ID;
//    @Column(name="counter")
//    private Integer counter;
//    @Column(name="first_name")
//    private String firstName;
//    @Column(name="last_name")
//    private String lastName;
//    @Column(name="mobile_number")
//    private String mobileNumber;
//    @Column(name="email")
//    private String email;
//    @Column(name="username")
//    private String username;
//    @Column(name="password")
//    private String password;
//    @Column(name="status")
//    private Integer status;
//    @OneToMany(mappedBy = "createdID")
//    private Set<Bug> createdBugs;
//    @OneToMany(mappedBy = "assignedID")
//    private Set<Bug> assignedBugs;
//    @OneToMany(mappedBy="userID")
//    private Set<Comment> comments;
//
//    @ManyToMany
//    @JoinTable(
//            name="users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    Set<Permission> roles;
//
//    public Integer getID() {
//        return ID;
//    }
//
//    public void setID(Integer ID) {
//        this.ID = ID;
//    }
//
//    public Integer getCounter() {
//        return counter;
//    }
//
//    public void setCounter(Integer counter) {
//        this.counter = counter;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public User(){
//    }
//
//    public Set<Bug> getCreatedBugs() {
//        return createdBugs;
//    }
//
//    public void setCreatedBugs(Set<Bug> createdBugs) {
//        this.createdBugs = createdBugs;
//    }
//
//    public Set<Bug> getAssignedBugs() {
//        return assignedBugs;
//    }
//
//    public void setAssignedBugs(Set<Bug> assignedBugs) {
//        this.assignedBugs = assignedBugs;
//    }
//
//    public Set<Permission> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Permission> roles) {
//        this.roles = roles;
//    }
//}


package entity;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name= User.FIND_ALL_USERS, query = "select u from User u"),
        @NamedQuery(name=User.DELETE_USER, query="Delete from User u where u.ID = :user"),
        @NamedQuery(name = User.CHECK_IF_USERNAME_UNIQUE,  query = "select count(u) from User u" +
        " where u.username = :username"),
        @NamedQuery(name=User.SELECT_BY_USERNAME_AND_PASSWORD, query = "select u from User u" +
        " where u.username = :username and u.password = :password"),
        @NamedQuery(name=User.GET_USER_WITH_USERNAME, query="select u from User u where u.username = :username"),

})
public class User {
    public static final String FIND_ALL_USERS = "findAllUsers";
    public static final String DELETE_USER = "deleteUser";
    public static final String CHECK_IF_USERNAME_UNIQUE = "checkUsernameUnique";
    public static final String SELECT_BY_USERNAME_AND_PASSWORD = "user.selectByUsernameAndID";
    public static final String GET_USER_WITH_USERNAME = "user.getUserCounter";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "counter")
    private Integer counter;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "status")
    private Integer status;
    @OneToMany(mappedBy = "createdID", cascade = CascadeType.PERSIST)
    private Set<Bug> createdBugs;
    @OneToMany(mappedBy = "assignedID", cascade = CascadeType.PERSIST)
    private Set<Bug> assignedBugs;
    @OneToMany(mappedBy="userID", cascade = CascadeType.PERSIST)
    private Set<Comment> comments;
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = {@JoinColumn(name= "role_id")})
    private List<Role> roleList;

//    @OneToMany(targetEntity = Notification.class, mappedBy = "userID", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @JoinColumn(name="user_id")
//    private Set<Notification> notifications;

        //adds two users
//    @OneToMany(targetEntity = Notification.class, mappedBy = "userID", cascade = CascadeType.PERSIST)
//    @JoinColumn(name="user_id")
//    private Set<Notification> notifications;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name="user_id", referencedColumnName = "ID")
    private Set<Notification> notifications;


    public User() {
    }


    public Set<Bug> getCreatedBugs() {
        return createdBugs;
    }

    public void setCreatedBugs(Set<Bug> createdBugs) {
        this.createdBugs = createdBugs;
    }

    public Set<Bug> getAssignedBugs() {
        return assignedBugs;
    }

    public void setAssignedBugs(Set<Bug> assignedBugs) {
        this.assignedBugs = assignedBugs;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getID() {
        return ID;
    }
    public void setID(Integer id) {
        this.ID = id;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(counter, user.counter) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(mobileNumber, user.mobileNumber) &&
                Objects.equals(password, user.password) &&
                Objects.equals(username, user.username) &&
                Objects.equals(status, user.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(counter, firstName, lastName, email, mobileNumber, password, username, status);
    }
}