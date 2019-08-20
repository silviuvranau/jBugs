package dao;

import entity.Permission;

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
public class PermissionDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public PermissionDao() {
    }

    public List<Permission> findAllPermissions() {
        Query query = entityManager.createNamedQuery(Permission.FIND_ALL_PERMISSIONS, Permission.class);
        List<Permission> permissions = query.getResultList();
        return permissions;
    }
}
