package ro.msg.edu.jbugs.managers.implementations;

import dao.PermissionDao;
import entity.Permission;
import entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionManagerTest {

    @InjectMocks
    private PermissionManager permissionManager;

    @Mock
    private PermissionDao permissionDao;

    public Permission createPermission() {
        Permission permission = new Permission();
        permission.setId(1);
        permission.setDescription("Testing permission.");
        permission.setType("BUG_MANAGEMENT");
        Role role = new Role();
        role.setId(1);
        role.setType("ADM");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        permission.setRoles(roles);

        return permission;
    }

    @Test
    public void findAllPermissions() {
        Permission perm = createPermission();
        Permission perm2 = createPermission();
        perm2.setId(2);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(perm);
        permissions.add(perm2);

        when(permissionDao.findAllPermissions()).thenReturn(permissions);

        assertEquals(2, permissionManager.findAllPermissions().size());
    }
}