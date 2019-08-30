package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugAttachmentWrapperDTO implements Serializable {

    private BugDTO bugDTO;
    private AttachmentDTO attachmentDTO;

    public BugAttachmentWrapperDTO() {
    }

    public BugDTO getBugDTO() {
        return bugDTO;
    }

    public void setBugDTO(BugDTO bugDTO) {
        this.bugDTO = bugDTO;
    }

    public AttachmentDTO getAttachmentDTO() {
        return attachmentDTO;
    }

    public void setAttachmentDTO(AttachmentDTO attachmentDTO) {
        this.attachmentDTO = attachmentDTO;
    }

    public BugAttachmentWrapperDTO(BugDTO bugDTO, AttachmentDTO attachmentDTO) {
        this.bugDTO = bugDTO;
        this.attachmentDTO = attachmentDTO;
    }
}
