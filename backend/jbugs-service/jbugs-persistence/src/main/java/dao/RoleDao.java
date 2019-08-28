package dao;

import entity.Permission;
import entity.Role;
import exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; Pricop Stefania.
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

    public void modifyRolePermission(Role role, Permission permission) throws BusinessException {
        Query query = entityManager.createNativeQuery("Select * from roles_permissions where role_id = ?1 and permission_id = ?2");
        query.setParameter(1, role.getId());
        query.setParameter(2, permission.getId());
        boolean permissionExistsToRole = query.getResultList().size() == 0;
        if (permissionExistsToRole) {
            addPermissionToRole(role, permission);
        } else {
            deletePermissionFromRole(role, permission);
        }
    }

    public void addPermissionToRole(Role role, Permission permission) throws BusinessException {
        Role roleToUpdate = entityManager.find(Role.class, role.getId());
        Permission permissionToUpdate = entityManager.find(Permission.class, permission.getId());

        if (roleToUpdate == null || permissionToUpdate == null) {
            throw new BusinessException("msg-001", "Entities do not exist.");
        } else {
            roleToUpdate.getPermissions().add(permissionToUpdate);
        }
    }

    public void deletePermissionFromRole(Role role, Permission permission) throws BusinessException {
        Role roleToUpdate = entityManager.find(Role.class, role.getId());
        Permission permissionToUpdate = entityManager.find(Permission.class, permission.getId());

        if (roleToUpdate == null || permissionToUpdate == null) {
            throw new BusinessException("msg-001", "Entities do not exist.");
        } else {
            roleToUpdate.getPermissions().remove(permissionToUpdate);
            permissionToUpdate.getRoles().remove(roleToUpdate);
        }
    }

    public Role findRole(Integer id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }
}
