package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

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
    private Blob attContent;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bug;

    public Integer getId() {
        return id;
    }

    public Bug getBug() {
        return bug;
    }

    public Blob getAttContent() {
        return attContent;
    }

    public void setAttContent(Blob attContent) {
        this.attContent = attContent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
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
