package ro.msg.edu.jbugs.managers.implementations;

import dao.CommentDao;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.CommentManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless //tells container that its an EJB
@Interceptors(Interceptor.class)
public class CommentManager implements CommentManagerRemote {
    @EJB
    private CommentDao commentDao;

    public Integer deleteComments() {
        return commentDao.deleteComments();
    }
}
