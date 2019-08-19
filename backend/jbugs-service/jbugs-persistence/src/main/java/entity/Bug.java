package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name="bugs")
@NamedQueries({
        @NamedQuery(name = Bug.GET_ALL_BUGS,query = "SELECT b FROM Bug b"),
        @NamedQuery(name= Bug.GET_BY_CREATED_ID, query = "SELECT b FROM Bug b " +
                "WHERE b.createdID = :user"),
        @NamedQuery(name= Bug.GET_BY_ASSIGNED_ID, query = "SELECT b FROM Bug b " +
                "WHERE b.assignedID = :user"),
//        @NamedQuery(name=Bug.DELETE_BUGS, query = "DELETE FROM Bug b " +
//                "WHERE b.targetDate < :date")
        @NamedQuery(name=Bug.DELETE_BUGS, query = "Update Bug b set b.status= 'closed' " +
                "WHERE b.targetDate < :date and b.status <> 'closed'")
})
public class Bug extends BaseEntity implements Serializable {
    public static final String GET_ALL_BUGS = "findAllBugs";
    public static final String GET_BY_CREATED_ID = "findByCreatedId";
    public static final String GET_BY_ASSIGNED_ID = "findByAssignedId";
    public static final String DELETE_BUGS="deleteBugsWhoExceededDate";
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer ID;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="version")
    private String version;
    @Column(name="targetDate")
    private Date targetDate;
    @Column(name="fixedVersion")
    private String fixedVersion;
    @Column(name="severity")
    private String severity;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="CREATED_ID", referencedColumnName = "ID")
    private User createdID;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ASSIGNED_ID",referencedColumnName = "ID")
    private User assignedID;
    @Column(name="status")
    private String status;

    @OneToMany(mappedBy="bugID")
    private Set<Comment> comments;

//    public Integer getID() {
//        return ID;
//    }
//
//    public void setID(Integer ID) {
//        this.ID = ID;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public User getCreatedID() {
        return createdID;
    }

    public void setCreatedID(User createdID) {
        this.createdID = createdID;
    }

    public User getAssignedID() {
        return assignedID;
    }

    public void setAssignedID(User assignedID) {
        this.assignedID = assignedID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bug(){}
}
