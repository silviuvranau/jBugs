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
@NamedQueries({
        @NamedQuery(name = Attachment.GET_ALL_ATTACHMENTS, query = "SELECT a FROM Attachment a"),
        @NamedQuery(name = Attachment.DELETE_ATTACHMENT, query = "Delete from Attachment a where a.id = :attachment"),
        @NamedQuery(name = Attachment.FIND_ATT_OF_BUG, query = "Select a from Attachment a where a.bug = :bug")
})
public class Attachment implements Serializable {
    public static final String GET_ALL_ATTACHMENTS = "findAllAttachments";
    public static final String DELETE_ATTACHMENT = "deleteAttachment";
    public static final String FIND_ATT_OF_BUG = "findAttOfBug";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name="att_content")
    private byte[] attContent;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bug;

    public Integer getId() {
        return id;
    }

    public Bug getBug() {
        return bug;
    }


    public byte[] getAttContent() {
        return attContent;
    }

    public void setAttContent(byte[] attContent) {
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
