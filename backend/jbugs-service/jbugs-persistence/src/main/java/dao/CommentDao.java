package dao;

import entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class CommentDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public CommentDao(){}

    public Integer deleteComments(){
        Query query = entityManager.createNamedQuery(Comment.DELETE_COMMENTS, Comment.class);
        Date currentDate = new Date();
        System.out.println(currentDate);
        long milliseconds = (long) 365 * 24 * 60 * 60 * 1000;
        Date oneYearAgo = new Date(currentDate.getTime() - milliseconds);
        query.setParameter("date", oneYearAgo);
        Integer deletedComments = query.executeUpdate();
        return deletedComments;
    }
}
