package dao;

import entity.Bug;
import entity.User;
import entity.enums.Status;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless //tells container that its an EJB
public class BugDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public BugDao() {
    }


    public Bug insertBug(Bug bug) {
        entityManager.persist(bug);
        entityManager.flush();
        System.out.println(bug.getId());
        return bug;
    }

    public Bug findBug(Integer id) {
        Bug bug = entityManager.find(Bug.class, id);
        return bug;
    }

    public List<Bug> findAllBugs() {
        Query query = entityManager.createNamedQuery(Bug.GET_ALL_BUGS, Bug.class);
        List<Bug> bugs = query.getResultList();
        return bugs;
    }

    public List<Bug> findByCreatedId(User user) {
        Query query = entityManager.createNamedQuery(Bug.GET_BY_CREATED_ID, Bug.class);
        query.setParameter("user",user);
        List<Bug> bugs =  query.getResultList();
        return bugs;
    }

    public List<Bug> findByAssignedId(User user) {
        Query query = entityManager.createNamedQuery(Bug.GET_BY_ASSIGNED_ID, Bug.class);
        query.setParameter("user",user);
        List<Bug> bugs =  query.getResultList();
        return bugs;
    }

    public Integer deleteBugs(){
        Query query = entityManager.createNamedQuery(Bug.DELETE_BUGS, Bug.class);
        query.setParameter("date", new Date());
        Integer deletedBugs = query.executeUpdate();
        entityManager.flush();
        return deletedBugs;
    }

    /**
     * Method that returns for a specific user how many unclosed bugs there are.
     *
     * @param user
     * @return
     */
    public Integer getClosedBugsByAssignedId(User user) {
        Query query = entityManager.createNamedQuery(Bug.GET_CLOSED_BUGS_BY_ASSIGNED_ID, Bug.class);
        query.setParameter("user", user);
        query.setParameter("status", Status.CLOSED);
        int count = ((Number) query.getSingleResult()).intValue();
        return count;
    }
}