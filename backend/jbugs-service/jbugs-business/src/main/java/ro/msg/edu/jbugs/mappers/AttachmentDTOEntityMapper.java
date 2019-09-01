package ro.msg.edu.jbugs.mappers;

import entity.Attachment;
import ro.msg.edu.jbugs.dto.AttachmentDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachmentDTOEntityMapper {

    public static Attachment getAttachmentFromDto(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setId(attachmentDTO.getId());
        attachment.setAttContent(attachmentDTO.getAttContent());
        attachment.setBug(BugDTOEntityMapper.getBugFromDto(attachmentDTO.getBug()));

        return attachment;
    }

    public static AttachmentDTO getDtoFromAttachment(Attachment attachment) {
        if (attachment != null) {
            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setId(attachment.getId());
            attachmentDTO.setAttContent(attachment.getAttContent());
            attachmentDTO.setBug(BugDTOEntityMapper.getDtoFromBug(attachment.getBug()));
            return attachmentDTO;
        }
        return null;

    }
}
