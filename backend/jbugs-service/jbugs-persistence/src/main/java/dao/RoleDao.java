package dao;

import entity.Permission;
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

    public void modifyRolePermission(Role role, Permission permission) {
        Query query = entityManager.createNativeQuery("Select * from roles_permissions where role_id = ?1 and permission_id = ?2");
        query.setParameter(1, role.getId());
        query.setParameter(2, permission.getId());
        boolean toAdd = query.getResultList().size() == 0;
        if (toAdd) {
            Query queryAdd = entityManager.createNativeQuery("Insert into roles_permissions(role_id, permission_id) values (?1, ?2)");
            queryAdd.setParameter(1, role.getId());
            queryAdd.setParameter(2, permission.getId());
            Integer updatedRows = queryAdd.executeUpdate();
            //return updatedRows;
        } else {
            Query queryDelete = entityManager.createNativeQuery("Delete from roles_permissions where role_id = ?1");
            queryDelete.setParameter(1, role.getId());
            Integer updatedRows = queryDelete.executeUpdate();
            //return updatedRows;
        }
    }
}
