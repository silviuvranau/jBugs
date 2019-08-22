package ro.msg.edu.jbugs.managers.implementations;

import dao.RoleDao;
import entity.Role;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.RoleManagerRemote;
import ro.msg.edu.jbugs.mappers.RoleDTOEntityMapper;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Interceptors(Interceptor.class)
public class RoleManager implements RoleManagerRemote {
    @EJB
    RoleDao roleDao;

    @Override
    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleDao.findAll();
        return roles.stream().map(RoleDTOEntityMapper :: getDtoFromRole).collect(Collectors.toList());
    }
}
