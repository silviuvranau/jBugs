package ro.msg.edu.jbugs.managers.implementations;

import com.sun.mail.iap.Argument;
import dao.RoleDao;
import entity.Permission;
import entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.mappers.PermissionDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.RoleDTOEntityMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Document me.
 *
 * @author msg systems AG; Pricop Stefania.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleManagerTest {
    @InjectMocks
    private RoleManager roleManager;

    @Mock
    private RoleDao roleDao;

    private Role firstTestingRole = new Role();
    private Role secondTestingRole = new Role();
    private Permission testingPermission = new Permission();

    public RoleManagerTest() {
        this.roleManager = new RoleManager();
        this.roleDao = new RoleDao();
    }
    @Test
    public void findAllRoles() {
        when(roleDao.findAllRoles()).thenReturn(Arrays.asList(firstTestingRole));
        List<RoleDTO> roles = roleManager.findAllRoles();
        assertEquals(roles.size(), 1);

        when(roleDao.findAllRoles()).thenReturn(Arrays.asList(firstTestingRole, secondTestingRole));
        roles = roleManager.findAllRoles();
        assertEquals(roles.size(), 2);
    }

    @Test
    public void modifyRolePermission() {
        firstTestingRole.setId(1);
        secondTestingRole.setId(1);
        testingPermission.setId(1);

        when(roleDao.modifyRolePermission(anyObject(), anyObject())).thenReturn(1);

        assertEquals(roleManager.modifyRolePermission(RoleDTOEntityMapper.getDtoFromRole(firstTestingRole),
                PermissionDTOEntityMapper.getDtoFromPermission(testingPermission)), Integer.valueOf(1));

        when(roleDao.modifyRolePermission(secondTestingRole, testingPermission)).thenReturn(1);
        assertEquals(roleManager.modifyRolePermission(RoleDTOEntityMapper.getDtoFromRole(secondTestingRole),
                PermissionDTOEntityMapper.getDtoFromPermission(testingPermission)), Integer.valueOf(1));

//        ArgumentCaptor<RoleDTO> roleCaptor = ArgumentCaptor.forClass(RoleDTO.class);
//        ArgumentCaptor<PermissionDTO> permissionCaptor = ArgumentCaptor.forClass(PermissionDTO.class);

        //verify(mock()).modifyRolePermission(roleCaptor.capture(), permissionCaptor.capture());

//        assertEquals(roleCaptor.getValue().getId(), firstTestingRole.getId());
//        assertEquals(permissionCaptor.getValue().getId(), testingPermission.getId());

//
    }
}