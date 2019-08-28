package ro.msg.edu.jbugs.dto;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugAttachmentDTO {

    private BugDTO bugDto;
    private AttachmentDTO attachmentDTO;

    public BugDTO getBugDto() {
        return bugDto;
    }

    public void setBugDto(BugDTO bugDto) {
        this.bugDto = bugDto;
    }

    public AttachmentDTO getAttachmentDTO() {
        return attachmentDTO;
    }

    public void setAttachmentDTO(AttachmentDTO attachmentDTO) {
        this.attachmentDTO = attachmentDTO;
    }
}
