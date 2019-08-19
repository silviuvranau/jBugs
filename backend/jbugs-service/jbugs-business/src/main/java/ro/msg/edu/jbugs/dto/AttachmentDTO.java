package ro.msg.edu.jbugs.dto;

import entity.Bug;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachmentDTO implements Serializable {
    private Integer ID;
    private String attContent;
    private Bug idBug;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getAttContent() {
        return attContent;
    }

    public void setAttContent(String attContent) {
        this.attContent = attContent;
    }

    public Bug getIdBug() {
        return idBug;
    }

    public void setIdBug(Bug idBug) {
        this.idBug = idBug;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "ID=" + ID +
                ", attContent='" + attContent + '\'' +
                ", idBug=" + idBug +
                '}';
    }
}
