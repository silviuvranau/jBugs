package dao;

import entity.Attachment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class AttachmentDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public AttachmentDao() {
    }

    public Attachment insertAttachment(Attachment attachment) {
        entityManager.persist(attachment);
        entityManager.flush();
        return attachment;
    }
}
