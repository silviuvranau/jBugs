package ro.msg.edu.jbugs.mappers;

import entity.Attachment;
import ro.msg.edu.jbugs.dto.AttachmentDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachementDTOEntityMapper {

    public static Attachment getAttachementFromDto(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setId(attachmentDTO.getId());
        attachment.setAttContent(attachmentDTO.getAttContent());
        attachment.setBug(attachmentDTO.getBug());

        return attachment;
    }

    public static AttachmentDTO getDtoFromAttachement(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setAttContent(attachment.getAttContent());
        attachmentDTO.setBug(attachmentDTO.getBug());

        return attachmentDTO;
    }
}
