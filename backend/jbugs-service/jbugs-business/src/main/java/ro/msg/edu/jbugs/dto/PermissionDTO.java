package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class PermissionDTO implements Serializable {
    private Integer id;
    private String description;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "ID=" + id +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
