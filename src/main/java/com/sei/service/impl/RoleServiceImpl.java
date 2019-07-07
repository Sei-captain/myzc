package com.sei.service.impl;

import com.sei.bean.Role;
import com.sei.dao.RoleDao;
import com.sei.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public List<Role> queryRole(String queryText) throws Exception {
        return roleDao.queryRole(queryText);
    }

    @Override
    public List<Role> queryRoleById(Integer id) throws Exception {
        return roleDao.queryRoleById(id);
    }

    @Override
    public List<Integer> queryRoleIdById(Integer id) throws Exception {
        return roleDao.queryRoleIdById(id);
    }

    @Override
    public void addRole(String name) throws Exception {
        roleDao.addRole(name);
    }

    @Override
    public void deleteRole(Integer[] ids) throws Exception {
        for (Integer id : ids) {
            roleDao.deleteRoleById(id);
        }
    }

    @Override
    public void deleteRoleUserByRoleId(Integer[] ids) throws Exception {
        for (Integer roleid : ids) {
            roleDao.deleteRoleUserByRoleId(roleid);
        }
    }

    @Override
    public void deleteRolePermissionByRoleId(Integer[] ids) throws Exception {
        for (Integer roleid : ids) {
            roleDao.deleteRolePermissionByRoleId(roleid);
        }
    }
}
