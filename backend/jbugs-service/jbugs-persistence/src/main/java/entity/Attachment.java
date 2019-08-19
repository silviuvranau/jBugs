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
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="att_content")
    private String attContent;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bug;

    public String getAttContent() {
        return attContent;
    }

    public void setAttContent(String attContent) {
        this.attContent = attContent;
    }

    public Bug getIdBug() {
        return bug;
    }

    public void setIdBug(Bug idBug) {
        this.bug = idBug;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", attContent='" + attContent + '\'' +
                ", bug=" + bug +
                '}';
    }
}
