package dao;

import entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class RoleDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public RoleDao() {
    }

    public List<Role> findAllRoles() {
        Query query = entityManager.createNamedQuery(Role.FIND_ALL_ROLES, Role.class);
        List<Role> roles = query.getResultList();
        return roles;
    }
}
