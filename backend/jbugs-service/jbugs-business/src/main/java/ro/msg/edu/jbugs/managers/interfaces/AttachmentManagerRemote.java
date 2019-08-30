package ro.msg.edu.jbugs.managers.interfaces;

import entity.Attachment;
import entity.Bug;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface AttachmentManagerRemote {
    List<AttachmentDTO> getAllAttachments() throws BusinessException;
    AttachmentDTO insertAttachment(Attachment attachment);
    Integer deleteAttachment(Integer id);
    AttachmentDTO findAttachmentOfBug(Bug bug);
}
