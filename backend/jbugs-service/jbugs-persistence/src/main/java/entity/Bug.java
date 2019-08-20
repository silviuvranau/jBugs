package entity;

import entity.enums.Severity;
import entity.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
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
                "WHERE b.createdId = :user"),
        @NamedQuery(name= Bug.GET_BY_ASSIGNED_ID, query = "SELECT b FROM Bug b " +
                "WHERE b.assignedId = :user"),
//        @NamedQuery(name=Bug.DELETE_BUGS, query = "DELETE FROM Bug b " +
//                "WHERE b.targetDate < :date")
        @NamedQuery(name=Bug.DELETE_BUGS, query = "Update Bug b set b.status= 'closed' " +
                "WHERE b.targetDate < :date and b.status <> 'closed'")
})
public class Bug implements Serializable {
    public static final String GET_ALL_BUGS = "findAllBugs";
    public static final String GET_BY_CREATED_ID = "findByCreatedId";
    public static final String GET_BY_ASSIGNED_ID = "findByAssignedId";
    public static final String DELETE_BUGS="deleteBugsWhoExceededDate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="version")
    private String version;

    @Column(name="target_date")
    private String targetDate;

    @Column(name="fixed_version")
    private String fixedVersion;

    @Column(name="severity")
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="created_username", referencedColumnName = "username")
    private User createdId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="assigned_username",referencedColumnName = "username")
    private User assignedId;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy="bug")
    private Set<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

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

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public User getCreatedId() {
        return createdId;
    }

    public void setCreatedId(User createdId) {
        this.createdId = createdId;
    }

    public User getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(User assignedId) {
        this.assignedId = assignedId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Bug(){}

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity='" + severity + '\'' +
                ", createdId=" + createdId +
                ", assignedId=" + assignedId +
                ", status='" + status + '\'' +
                ", comments=" + comments +
                '}';
    }
}
