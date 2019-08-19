package dao;

import entity.Bug;
import entity.User;

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

    public BugDao(){};


    public Bug insertBug(Bug bug) {
        entityManager.persist(bug);
        entityManager.flush();
        System.out.println(bug.getID());
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
}