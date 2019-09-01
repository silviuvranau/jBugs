package ro.msg.edu.jbugs.managers.implementations;

import dao.AttachmentDao;
import entity.Attachment;
import entity.Bug;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.managers.interfaces.AttachmentManagerRemote;
import ro.msg.edu.jbugs.mappers.AttachmentDTOEntityMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class AttachmentManager implements AttachmentManagerRemote {
    @EJB
    private AttachmentDao attachmentDao;


    @Override
    public List<AttachmentDTO> getAllAttachments() throws BusinessException {
        List<Attachment> attachments = attachmentDao.findAllAttachments();
        return attachments.stream().map(AttachmentDTOEntityMapper::getDtoFromAttachment).collect(Collectors.toList());
    }

    @Override
    public AttachmentDTO insertAttachment(Attachment attachment) {
        return AttachmentDTOEntityMapper.getDtoFromAttachment(attachmentDao.insertAttachment(attachment));
    }

    @Override
    public Integer deleteAttachment(Integer id) {
        return attachmentDao.deleteAttachment(id);
    }

    @Override
    public AttachmentDTO findAttachmentOfBug(Bug bug) {
        return AttachmentDTOEntityMapper.getDtoFromAttachment(attachmentDao.findAttachmentOfBug(bug));
    }
}

