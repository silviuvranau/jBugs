package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleDTO implements Serializable {
    private Integer id;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "ID=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
