package dao;

import entity.Permission;
import entity.Role;
import exceptions.BusinessException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleDaoTest {

    @InjectMocks
    private RoleDao roleDao;

    @Mock
    private EntityManager entityManager;

    private Role firstTestingRole = new Role();
    private Role secondTestingRole = new Role();
    private Permission testingPermission = new Permission();

    @Test
    public void modifyRolePermissionWithAddCall() {
        firstTestingRole.setId(1);
        secondTestingRole.setId(1);
        testingPermission.setId(1);

        RoleDao roleDao = mock(RoleDao.class);
        Query query = mock(Query.class);

        try {
            doNothing().when(roleDao).modifyRolePermission(isA(Role.class), isA(Permission.class));
            List<Object> list = new ArrayList<>();
            when(query.getResultList()).thenReturn(list);
            roleDao.addPermissionToRole(firstTestingRole, testingPermission);
            //roleDao.deletePermissionFromRole(firstTestingRole, testingPermission);

            verify(roleDao, times(1)).addPermissionToRole(firstTestingRole, testingPermission);
        } catch (BusinessException e) {
        }
    }

    @Test
    public void modifyRolePermissionWithDeleteCall() {
        firstTestingRole.setId(1);
        secondTestingRole.setId(1);
        testingPermission.setId(1);

        RoleDao roleDao = mock(RoleDao.class);
        Query query = mock(Query.class);

        try {
            doNothing().when(roleDao).modifyRolePermission(isA(Role.class), isA(Permission.class));
            List<Object> list = new ArrayList<>();
            Object obj = new Object();
            list.add(obj);

            when(query.getResultList()).thenReturn(list);
            //roleDao.addPermissionToRole(firstTestingRole, testingPermission);
            roleDao.deletePermissionFromRole(firstTestingRole, testingPermission);

            verify(roleDao, times(1)).deletePermissionFromRole(firstTestingRole, testingPermission);
        } catch (BusinessException e) {
        }
    }
}
