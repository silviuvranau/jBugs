package dao;

import entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class RoleDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public Role findRole(Integer id){
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    public List<Role> findAll() {
        Query query = entityManager.createNamedQuery(Role.FIND_ALL_ROLES, Role.class);
        List<Role> users = query.getResultList();
        return users;
    }
}
