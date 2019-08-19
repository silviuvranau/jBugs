package ro.msg.edu.jbugs.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugDTO implements Serializable {
    private Integer ID;
    private String title;
    private String description;
    private String version;
    private Date targetDate;
    private String fixedVersion;
    private String severity;
    private UserDTO createdId;
    private UserDTO assignedId;
    private String status;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BugDTO{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate=" + targetDate +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity='" + severity + '\'' +
                ", createdId=" + createdId +
                ", assignedId=" + assignedId +
                ", status='" + status + '\'' +
                '}';
    }
}
