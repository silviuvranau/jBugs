package ro.msg.edu.jbugs.dto;


import entity.Comment;
import entity.enums.Severity;
import entity.enums.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugDTO implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String version;
    private String targetDate;
    private String fixedVersion;
    private Severity severity;
    private UserDTO createdId;
    private UserDTO assignedId;
    private Status status;
    private Set<CommentDTO> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BugDTO(){}

    public UserDTO getCreatedId() {
        return createdId;
    }

    public UserDTO getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(UserDTO assignedId) {
        this.assignedId = assignedId;
    }

    public void setCreatedId(UserDTO createdId) {
        this.createdId = createdId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "BugDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity=" + severity +
                ", createdId=" + createdId +
                ", assignedId=" + assignedId +
                ", status='" + status + '\'' +
                ", comments=" + comments +
                '}';
    }
}