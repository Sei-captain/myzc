package com.sei.service.impl;

import com.sei.bean.Icon;
import com.sei.bean.Permission;
import com.sei.dao.PermissionDao;
import com.sei.service.PermissionService;
import com.sei.util.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<Permission> queryAllPermission() throws Exception {
        return permissionDao.queryAllPermission();
    }

    @Override
    public void addPermission(Permission permission) throws Exception {
        permissionDao.addPermission(permission);
    }

    @Override
    public Permission queryPermissionById(Integer id) throws Exception {
        return permissionDao.queryPermissionById(id);
    }

    @Override
    public void updatePermission(Permission permission) throws Exception {
        permissionDao.updatePermission(permission);
    }

    @Override
    public List<Icon> queryIcon() throws Exception {
        return permissionDao.queryIcon();
    }

    @Override
    public void deletePermission(Integer id) throws Exception {
        permissionDao.deletePermission(id);
    }

    @Override
    public List<Integer> queryPermissionIdByRoleId(Integer id) throws Exception {
        return permissionDao.queryPermissionIdByRoleId(id);
    }

    @Override
    public void deletePermissionIdByRoleId(Integer roleid) throws Exception {
        permissionDao.deletePermissionIdByRoleId(roleid);
    }

    @Override
    public void addPermissionIdByRoleId(Integer roleid,Data data) throws Exception {
        List<Integer> ids = data.getIds();
        for (Integer permissionid : ids) {
            permissionDao.addPermissionIdByRoleId(roleid,permissionid);
        }
    }

    @Override
    public void deleteRoleIdByPermissionId(Integer id) throws Exception {
        permissionDao.deleteRoleIdByPermissionId(id);
    }
}
