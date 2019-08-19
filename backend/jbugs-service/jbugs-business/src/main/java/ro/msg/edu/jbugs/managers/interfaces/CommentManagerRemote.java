package ro.msg.edu.jbugs.managers.interfaces;

import javax.ejb.Remote;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface CommentManagerRemote {
    Integer deleteComments();
}
