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

    //Daca fac un when dao blabla pe metoda asta, si ii dau parametrii role, permission atunci sa faca thenReturn
    //'Crashuieste' cand apelez apoi in test metoda din roleManager pentru ca roleManager primeste doua DTO-uri si face conversia
    //ca sa apeleze metoda asta din dao
    //Si nu se mai point-eaza la acelasi obiect ca sa treaca testul
    //Referintele ma refer
    //Sper ca explic okay :)))
    // Wow. Stai. Tu akma vrei sa verifici daca chiar sa-au modificat datele in BD?
    ///Nuu. I know you can't, ca.i mock :))

    //Ce vreau sa zic e ca, metoda asta de jos, modifyRP, nu trebuie si ea testata?
    //Ajugne sa testez add&remove permission?
    // Ba da, trebuie testata. Ahso. Akma inteleg. Mna bun. Unde sa incepem.
    //Pai cu asta, modifyRP?
    //Caci cu celalalte doua cred ca ma descurc singura :D
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

    // Same here
    //Okay
    //Si dupa..?
    //I mean, problema era ca daca fac un when
    // Mna. Trebuie sa facu un RoleDAOTest + sa mockuiesti EntityManager si sa verifici daca metodele sunt apelate.
    // Te descurci sau facem impreuna?

    //Nu e nevoie, ma descurc cu aia. /Thanks :) Npc. :* Spor
    //Multuu :)

    private void addPermissionToRole(Role role, Permission permission) throws BusinessException {
        Role roleToUpdate = entityManager.find(Role.class, role.getId());
        Permission permissionToUpdate = entityManager.find(Permission.class, permission.getId());

        if (roleToUpdate == null || permissionToUpdate == null) {
            throw new BusinessException("msg-001", "Entities do not exist.");
        } else {
            roleToUpdate.getPermissions().add(permissionToUpdate);
        }
    }

    // Ar trebui sa stergi private si sa scrii 2 teste pentru metoda asta 1 - Exception + 1 -  remove
    private void deletePermissionFromRole(Role role, Permission permission) throws BusinessException {
        Role roleToUpdate = entityManager.find(Role.class, role.getId());
        Permission permissionToUpdate = entityManager.find(Permission.class, permission.getId());

        if (roleToUpdate == null || permissionToUpdate == null) {
            throw new BusinessException("msg-001", "Entities do not exist.");
        } else {
            roleToUpdate.getPermissions().remove(permissionToUpdate);
            permissionToUpdate.getRoles().remove(roleToUpdate);
        }
    }
}
