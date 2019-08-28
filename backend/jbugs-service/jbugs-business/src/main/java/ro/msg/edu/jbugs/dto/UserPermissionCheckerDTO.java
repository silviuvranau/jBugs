package ro.msg.edu.jbugs.dto;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserPermissionCheckerDTO {
    private String username;
    private String requiredPermissionType;

    public String getUsername() {
        return username;
    }

    public String getRequiredPermissionType() {
        return requiredPermissionType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRequiredPermissionType(String requiredPermissionType) {
        this.requiredPermissionType = requiredPermissionType;
    }
}
