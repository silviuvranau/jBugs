package dao;

import com.google.common.hash.Hashing;
import entity.User;
import exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
//tells container that its an EJB
@Stateless
public class UserDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public UserDao(){};

    public User findUser(Integer id) throws BusinessException {
        User user = entityManager.find(User.class, id);
        if (user == null)
            throw new BusinessException("msg_003", "User not found exception");
        return user;
    }

    public User insertUser(User user){
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    public List<User> findAll() {
        Query query = entityManager.createNamedQuery(User.FIND_ALL_USERS, User.class);
        List<User> users = query.getResultList();
        return users;
    }

    public Long getCreatedBugs(User user){
        Query query = entityManager.createNativeQuery("SELECT COUNT(b.CREATED_ID) FROM bugs b where b.CREATED_ID = ?1;");
        query.setParameter(1,user.getId());
        query.setParameter(1, user.getId());
        Object resultSet = query.getSingleResult();
        Long numberOfCreatedBugs = (Long)resultSet;
        return numberOfCreatedBugs;
    }

    public Integer deleteUser(Integer id){
        Query query = entityManager.createNamedQuery(User.DELETE_USER, User.class);
        query.setParameter("user", id);
        Integer deletedUser = query.executeUpdate();
        return deletedUser;
    }

    /**
     *
     * @param username
     * @return true if the username is unique, false otherwise
     * executes a query which counts the number of occurrences
     * of an username
     */
    public boolean checkUsernameUnique(String username){
        Long occurrences = entityManager.createNamedQuery(User.CHECK_IF_USERNAME_UNIQUE, Long.class)
                .setParameter("username", username)
                .getSingleResult();

        if (occurrences != 0) {
            return false;
        } else {
            return true;
        }
    }

    public User findUserByUsername(String username) throws BusinessException {
        Query query = entityManager.createNamedQuery(User.GET_USER_WITH_USERNAME, User.class);
        query.setParameter("username", username);
        try{
            //User foundUser = (User)query.getSingleResult();
            List<User> users = query.getResultList();
            if (users.size() > 0) {
                return users.get(0);
            } else {
                return null;
            }
            //return foundUser;
        }catch (NoResultException e){
            throw new BusinessException(e.getMessage(), "User with username does not exists");

        }
    }

    public User findUserByUsernameAndPassword(String username, String password) throws BusinessException {
        User user;
        try {
            user = (User) entityManager.createNamedQuery(User.SELECT_BY_USERNAME_AND_PASSWORD, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password).getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessException("msg_001", "Invalid credentials.");
        }
        return user;
    }
}
