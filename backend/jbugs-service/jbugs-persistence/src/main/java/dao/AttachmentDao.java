package dao;

import entity.Attachment;
import entity.Bug;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    public Integer deleteAttachment(Integer id) {
        Query query = entityManager.createNamedQuery(Attachment.DELETE_ATTACHMENT, Attachment.class);
        query.setParameter("attachment", id);
        Integer deleteAttachment = query.executeUpdate();
        return deleteAttachment;
    }

    public List<Attachment> findAllAttachments() {
        Query query = entityManager.createNamedQuery(Attachment.GET_ALL_ATTACHMENTS, Attachment.class);
        List<Attachment> attachments = query.getResultList();
        return attachments;
    }

    public Attachment findAttachmentOfBug(Bug bug) {
        Query query = entityManager.createNamedQuery(Attachment.FIND_ATT_OF_BUG, Attachment.class);
        query.setParameter("bug", bug);
        List<Attachment> attachments = query.getResultList();
        if (attachments.size() != 0) {
            return attachments.get(0);
        } else {
            return null;
        }
    }
}
