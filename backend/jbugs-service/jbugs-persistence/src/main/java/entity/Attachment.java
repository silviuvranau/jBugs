package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name="attachments")
public class Attachment extends BaseEntity implements Serializable {
    @Column(name="attContent")
    private String attContent;
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="id_bug", referencedColumnName = "ID")
    private Bug idBug;

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
        return "Attachment{" +
                "attContent='" + attContent + '\'' +
                ", idBug=" + idBug +
                '}';
    }
}
