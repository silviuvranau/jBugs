package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachmentDTO implements Serializable {
    private Integer id;
    private byte[] attContent;
    private BugDTO bug;

    public AttachmentDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BugDTO getBug() {
        return bug;
    }

    public void setBug(BugDTO bug) {
        this.bug = bug;
    }

    public byte[] getAttContent() {
        return attContent;
    }

    public void setAttContent(byte[] attContent) {
        this.attContent = attContent;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "id=" + id +
                ", attContent='" + attContent + '\'' +
//                ", bug=" + bug +
                '}';
    }
}
