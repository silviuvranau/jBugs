package ro.msg.edu.jbugs.dto;

import entity.Bug;

import java.io.Serializable;
import java.sql.Blob;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachmentDTO implements Serializable {
    private Integer id;
    private Blob attContent;
    private Bug bug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getAttContent() {
        return attContent;
    }

    public void setAttContent(Blob attContent) {
        this.attContent = attContent;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "id=" + id +
                ", attContent='" + attContent + '\'' +
                ", bug=" + bug +
                '}';
    }
}
